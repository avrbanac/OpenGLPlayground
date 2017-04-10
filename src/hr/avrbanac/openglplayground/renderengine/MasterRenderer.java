
package hr.avrbanac.openglplayground.renderengine;

import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.entities.Entity;
import hr.avrbanac.openglplayground.entities.Light;
import hr.avrbanac.openglplayground.maths.Matrix4f;
import hr.avrbanac.openglplayground.models.TexturedModel;
import hr.avrbanac.openglplayground.shaders.StaticShader;
import hr.avrbanac.openglplayground.shaders.TerrainShader;
import hr.avrbanac.openglplayground.terrains.Terrain;
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
 * @version 1.0.0
 */
public class MasterRenderer {
    private StaticShader shader;
    private EntityRenderer renderer;
    private TerrainShader terrainShader;
    private TerrainRenderer terrainRenderer;
    
    private Matrix4f projectionMatrix;
    
    private Map<TexturedModel, List<Entity>> entities = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();

    public MasterRenderer(int width, int height, float fov) {
        // don't render faces that will never be seen
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        
        projectionMatrix = Matrix4f.projection(width, height, fov);
        
        shader      = new StaticShader();
        renderer    = new EntityRenderer(shader, projectionMatrix);
        
        terrainShader   = new TerrainShader();
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }
    
    public void render(Light sun, Camera camera) {
        prepare();
        
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();
        
        terrainShader.start();
        terrainShader.loadLight(sun);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        
        // clear hashmap or else it would build up with each render call
        entities.clear();
        terrains.clear();
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
    
    public void processTerrain(Terrain terrain) {
        terrains.add(terrain);
    }
    
    public void prepare() {
        // test which triangles are "in front" of others
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        // clear the color from previous frame
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        // clear everything with background color (black in this case)
        GL11.glClearColor(0, 0, 0, 1);
    }
    
    public void cleanUp() {
        shader.cleanUp();
        terrainShader.cleanUp();
    }
}