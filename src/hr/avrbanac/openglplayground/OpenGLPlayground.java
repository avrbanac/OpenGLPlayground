
package hr.avrbanac.openglplayground;

import hr.avrbanac.openglplayground.display.DisplayManager;
import hr.avrbanac.openglplayground.entities.Entity;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.entities.LampStand;
import hr.avrbanac.openglplayground.entities.Light;
import hr.avrbanac.openglplayground.entities.Player;
import hr.avrbanac.openglplayground.renderers.GuiRenderer;
import hr.avrbanac.openglplayground.renderers.MasterRenderer;
import hr.avrbanac.openglplayground.maths.Vector2f;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.loaders.ModelLoader;
import hr.avrbanac.openglplayground.loaders.OBJFileLoader;
import hr.avrbanac.openglplayground.maths.Vector4f;
import hr.avrbanac.openglplayground.models.TexturedModel;
import hr.avrbanac.openglplayground.models.ModelData;
import hr.avrbanac.openglplayground.normalMappingObjConverter.NormalMappedObjLoader;
import hr.avrbanac.openglplayground.renderers.WaterRenderer;
import hr.avrbanac.openglplayground.shaders.WaterShader;
import hr.avrbanac.openglplayground.textures.GuiTexture;
import hr.avrbanac.openglplayground.textures.ModelTexture;
import hr.avrbanac.openglplayground.textures.TerrainTexture;
import hr.avrbanac.openglplayground.textures.TerrainTexturePack;
import hr.avrbanac.openglplayground.surfaces.Terrain;
import hr.avrbanac.openglplayground.surfaces.WaterTile;
import hr.avrbanac.openglplayground.utils.MousePicker;
import hr.avrbanac.openglplayground.utils.WaterFrameBuffers;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

/**
 * Main class with application point of entry.
 * 
 * @author avrbanac
 * @version 1.0.18
 */
public class OpenGLPlayground implements Runnable {

    private static GLFWErrorCallback errorCallback = GLFWErrorCallback.createPrint(System.err);
    private Thread gameThread;
    public boolean isRunning = true;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // handle errors via error callback
        glfwSetErrorCallback(errorCallback);
        
        OpenGLPlayground game = new OpenGLPlayground();
        game.start();
        
        // release error callback
        errorCallback.free();
    }
    
    /**
     * Game thread entry point.
     * 
     * @see Runnable
     */
    public void start() {
        isRunning = true;
        gameThread = new Thread(this, "Game loop");
        gameThread.start();
    }
    
    /**
     * Overridden method that serves as a loop body method.
     * 
     * @see Runnable
     */
    @Override
    public void run() {
        DisplayManager dm = new DisplayManager();
        dm.createDisplay();
        
        ModelLoader loader = new ModelLoader();
        
//        TexturedModel tree = new TexturedModel(
//                OBJSimpleLoader.loadObjModel("tree", loader),
//                new ModelTexture(loader.loadTexture("tree")));
//        tree.getTexture().setShineDamper(10);
        ModelData tree1Data = OBJFileLoader.loadOBJ("tree");
        TexturedModel tree1 = new TexturedModel(
                loader.loadToVAO(tree1Data.getVertices(), tree1Data.getTextureCoords(), tree1Data.getNormals(), tree1Data.getIndices()),
                new ModelTexture(loader.loadTexture("tree")));
        tree1.getTexture().setShineDamper(10);
        
        ModelData tree2Data = OBJFileLoader.loadOBJ("lowPolyTree");
        TexturedModel tree2 = new TexturedModel(
                loader.loadToVAO(tree2Data.getVertices(), tree2Data.getTextureCoords(), tree2Data.getNormals(), tree2Data.getIndices()),
                new ModelTexture(loader.loadTexture("lowPolyTree")));
        tree2.getTexture().setShineDamper(10);
        
        // using the same data model for 2 different textures
        ModelData grassData = OBJFileLoader.loadOBJ("grassModel");
        TexturedModel grass = new TexturedModel(
                loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(), grassData.getNormals(), grassData.getIndices()),
                new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setTransparency(true);
        grass.getTexture().setShineDamper(10);
        grass.getTexture().setUseFakeLighting(true);
        
        TexturedModel flower = new TexturedModel(
                loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(), grassData.getNormals(), grassData.getIndices()),
                new ModelTexture(loader.loadTexture("flower")));
        flower.getTexture().setTransparency(true);
        flower.getTexture().setShineDamper(10);
        flower.getTexture().setUseFakeLighting(true);
        
        ModelData fernData = OBJFileLoader.loadOBJ("fern");
        TexturedModel fern = new TexturedModel(
                loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices()),
                new ModelTexture(loader.loadTexture("fernAtlas")));
        fern.getTexture().setTransparency(true);
        fern.getTexture().setShineDamper(10);
        // texture atlas 2x2
        fern.getTexture().setNumberOfRows(2);
        
        ModelData bunnyData = OBJFileLoader.loadOBJ("bunny");
        TexturedModel bunny = new TexturedModel(
                loader.loadToVAO(bunnyData.getVertices(), bunnyData.getTextureCoords(), bunnyData.getNormals(), bunnyData.getIndices()),
                new ModelTexture(loader.loadTexture("white")));
        
        ModelData lampData = OBJFileLoader.loadOBJ("lamp");
        TexturedModel lamp = new TexturedModel(
                loader.loadToVAO(lampData.getVertices(), lampData.getTextureCoords(), lampData.getNormals(), lampData.getIndices()),
                new ModelTexture(loader.loadTexture("lamp")));


        Player player = new Player(bunny, new Vector3f(200,0,-200), 0, 180, 0, 0.3f);
        
        List<Entity> entities = new ArrayList<>();
        entities.add(player);
        
        Camera camera = new Camera(player);

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        
        Terrain terrain1 = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
        //Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap");
        List<Terrain> terrains = new ArrayList<>();
        terrains.add(terrain1);
        
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            entities.add(new Entity(tree1, randomPos3f(random, terrain1), 0, 0, 0, 3));
            entities.add(new Entity(tree2, randomPos3f(random, terrain1), 0, random.nextFloat() * 360, 0, 0.5f));
            entities.add(new Entity(grass, randomPos3f(random, terrain1), 0, random.nextFloat() * 360, 0, 1));
            entities.add(new Entity(fern, random.nextInt(4), randomPos3f(random, terrain1), 0, random.nextFloat() * 360, 0, 0.6f));
            entities.add(new Entity(flower, randomPos3f(random, terrain1), 0, random.nextFloat() * 360, 0, 1f));
        }
        
        LampStand lamp1 = new LampStand(lamp, new Vector3f(184, -4.7f, -293), 0, 0, 0, 1, new Vector3f(2, 0, 0));
        LampStand lamp2 = new LampStand(lamp, new Vector3f(370, 4.2f, -300), 0, 0, 0, 1, new Vector3f(0, 2, 2));
        //LampStand lamp3 = new LampStand(lamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1, new Vector3f(2, 2, 0));
        
        entities.add(lamp1);
        entities.add(lamp2);
        //entities.add(lamp3);
        
        Light sun = new Light(new Vector3f(0,1000, -7000), new Vector3f(0.4f, 0.4f, 0.4f));
        
        List<Light> lights = new ArrayList<>();
        // "sun" with no attenuation; lower brightness
        lights.add(sun);
        lights.add(lamp1.getLampLight());
        lights.add(lamp2.getLampLight());
        //lights.add(lamp3.getLampLight());
        
        //normal entities
        List<Entity> normalEntities = new ArrayList<>();
        
        TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader),
                new ModelTexture(loader.loadTexture("barrel")));
        barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal"));
        barrelModel.getTexture().setShineDamper(10);
        barrelModel.getTexture().setReflectivity(0.5f);
        
        normalEntities.add(new Entity(barrelModel, new Vector3f(250, 15, -250), 0, 0, 0, 1f));
        
        
        MasterRenderer renderer = new MasterRenderer(dm.getWidth(), dm.getHeight(), Globals.FOV, loader);
        
        List<GuiTexture> guis = new ArrayList<>();
        GuiTexture gui = new GuiTexture(
                loader.loadTexture("carrot"),
                new Vector2f(0.75f, 0.75f),
                new Vector2f(0.20f, 0.20f));
        guis.add(gui);
        
        GuiRenderer guiRenderer = new GuiRenderer(loader);
        
        // instead of terrain it is possible to add support for multiple terrains (Terrain[])
        MousePicker picker = new MousePicker (camera,renderer.getProjectionMatrix(), terrain1);
   
        LampStand mouseLamp = new LampStand(lamp, player.getPosition(), 0, 0, 0, 1, new Vector3f(2, 0, 2));
        entities.add(mouseLamp);
        lights.add(mouseLamp.getLampLight());
        
        // water renderer setup
        WaterShader waterShader = new WaterShader();
        WaterFrameBuffers fbos = new WaterFrameBuffers();
        WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), fbos);
        List<WaterTile> waters = new ArrayList<>();
        WaterTile water = new WaterTile(80, -128, -5);
        waters.add(water);
        
        
        while(isRunning) {
            camera.move();
            
            // calculate which terrain player is standing on (if multiple) 
            player.move(terrain1);
            
            // mouse picker
            picker.update();
            Vector3f terrainPoint = picker.getCurrentTerrainPoint();
            if (terrainPoint != null) {
                mouseLamp.setPosition(terrainPoint);
                mouseLamp.getLampLight().setPosition(new Vector3f(terrainPoint.x, terrainPoint.y+20f, terrainPoint.z));
            }
            //System.out.println((terrainPoint != null ? terrainPoint.toString() : "N/A"));
            
            // we are only ever going to use gl_clip_distance[0]
            GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
            
            // render reflection texture
            fbos.bindReflectionFrameBuffer();
            camera.invertHeight(water.getHeight()).invertPitch();
            renderer.renderScene(terrains, entities, normalEntities, lights, camera, new Vector4f(0, 1, 0, -water.getHeight() + 0.5f)); // edge fix 0.5f
            camera.invertHeight(water.getHeight()).invertPitch();
            
            // render refraction texture
            fbos.bindRefractionFrameBuffer();
            renderer.renderScene(terrains, entities, normalEntities, lights, camera, new Vector4f(0, -1, 0, water.getHeight()));
            
            // disable clip culling for scene rendering
            GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
            
            // bind default frame buffer again
            fbos.unbindCurrentFrameBuffer();
            
            // render everything to screen
            renderer.renderScene(terrains, entities, normalEntities, lights, camera, new Vector4f(0,1,0,5));
            
            // rendering of water and guis are done only for default frame buffer
            waterRenderer.render(waters, camera, sun);
            guiRenderer.render(guis);
            
            dm.renderDisplay();
            dm.updateDisplay();
            
            // escape key pressed or close window button
            if(glfwWindowShouldClose(dm.getWindow())) {
                isRunning = false;
            }
        }
        // clean up water frame buffers
        fbos.cleanUp();
        
        // clean up water shader
        waterShader.cleanUp();
        
        // clean up gui shaders
        guiRenderer.cleanUp();
        
        // clean up shaders
        renderer.cleanUp();
        
        // clean up all the VAOs and VBOs
        loader.cleanUp();
        
        // close display
        dm.closeDisplay();
    }
    
    private Vector3f randomPos3f (Random random, Terrain terrain) {
        float x = random.nextFloat() * 800;
        float z = random.nextFloat() * -800;
        float y = terrain.getHeightOfTerrain(x, z);
        
        return new Vector3f(x,y,z);
    }
    
}

