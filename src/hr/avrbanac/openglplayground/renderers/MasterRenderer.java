
package hr.avrbanac.openglplayground.renderers;

import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.entities.Entity;
import hr.avrbanac.openglplayground.entities.Light;
import hr.avrbanac.openglplayground.loaders.ModelLoader;
import hr.avrbanac.openglplayground.maths.Matrix4f;
import hr.avrbanac.openglplayground.maths.Vector4f;
import hr.avrbanac.openglplayground.models.TexturedModel;
import hr.avrbanac.openglplayground.normalMappingRenderer.NormalMappingRenderer;
import hr.avrbanac.openglplayground.shaders.StaticShader;
import hr.avrbanac.openglplayground.shaders.TerrainShader;
import hr.avrbanac.openglplayground.surfaces.Terrain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lwjgl.opengl.GL11;

/**
 * This is the main renderer class. This renderer will optimize rendering by 
 * grouping instances of the same 3D object together. Binding to the model,
 * binding to the texture, loading the shine settings and unbinding will now be
 * done once per model type (not once for every rendered model). Also, similar
 * optimization will be used while rendering terrain.
 * 
 * @author avrbanac
 * @version 1.0.18
 */
public class MasterRenderer {
    private final StaticShader shader;
    private final EntityRenderer renderer;
    private final TerrainShader terrainShader;
    private final TerrainRenderer terrainRenderer;
    
    private final Matrix4f projectionMatrix;
    
    private final Map<TexturedModel, List<Entity>> entities = new HashMap<>();
    private final List<Terrain> terrains = new ArrayList<>();
    
    private final SkyboxRenderer skyboxRenderer;
    
    private final NormalMappingRenderer normalMapRenderer;
    private final Map<TexturedModel, List<Entity>> nmEntities = new HashMap<>();

    public MasterRenderer(int width, int height, float fov, ModelLoader loader) {
        enableCulling();
        
        projectionMatrix = Matrix4f.projection(width, height, fov);
        
        shader      = new StaticShader();
        renderer    = new EntityRenderer(shader, projectionMatrix);
        
        terrainShader   = new TerrainShader();
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
        
        skyboxRenderer  = new SkyboxRenderer(loader, projectionMatrix);
        
        normalMapRenderer = new NormalMappingRenderer(projectionMatrix);
    }
    
    public void renderScene(
            List<Terrain> terrains,
            List<Entity> entities,
            List<Entity> normalEntities,
            List<Light> lights,
            Camera camera,
            Vector4f clipPlane) {
        
        terrains.forEach((terrain) -> {
            processTerrain(terrain);
        });
        entities.forEach((entity) -> {
            processEntity(entity);
        });
        normalEntities.forEach((entity) -> {
            processNMEntity(entity);
        });
        
        render(lights, camera, clipPlane);
    }
    
    public static void enableCulling() {
        // don't render faces that will never be seen
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }
    
    public static void disableCulling() {
        // don't render faces that will never be seen
        GL11.glDisable(GL11.GL_CULL_FACE);
    }
    
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
    
    public void render(List<Light> lights, Camera camera, Vector4f clipPlane) {
        prepare();
        
        shader.start();
        shader.loadClipPlane(clipPlane);
        shader.loadSkyColor(SKY_RED, SKY_GREEN, SKY_BLUE);
        shader.loadLights(lights);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();
        
        normalMapRenderer.render(nmEntities, clipPlane, lights, camera);
        terrainShader.start();
        terrainShader.loadClipPlane(clipPlane);
        terrainShader.loadSkyColor(SKY_RED, SKY_GREEN, SKY_BLUE);
        terrainShader.loadLights(lights);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        skyboxRenderer.render(camera, SKY_RED, SKY_GREEN, SKY_BLUE);
        
        // clear hashmap or else it would build up with each render call
        entities.clear();
        terrains.clear();
        nmEntities.clear();
    }
    
    public void processEntity(Entity entity) {
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }
    
    public void processNMEntity(Entity entity) {
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = nmEntities.get(entityModel);
        if(batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            nmEntities.put(entityModel, newBatch);
        }
    }
    
    public void processTerrain(Terrain terrain) {
        terrains.add(terrain);
    }
    
    public void prepare() {
        // test which triangles are "in front" of others
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        // clear the color from previous frame
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        // clear everything with background color (black in this case)
        GL11.glClearColor(SKY_RED, SKY_GREEN, SKY_BLUE, 1);
    }
    
    public void cleanUp() {
        shader.cleanUp();
        terrainShader.cleanUp();
        normalMapRenderer.cleanUp();
    }
}
