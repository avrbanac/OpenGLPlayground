
package hr.avrbanac.openglplayground;

import hr.avrbanac.openglplayground.entities.Entity;
import hr.avrbanac.openglplayground.display.DisplayManager;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.entities.Light;
import hr.avrbanac.openglplayground.entities.Player;
import hr.avrbanac.openglplayground.textures.GuiTexture;
import hr.avrbanac.openglplayground.renderers.GuiRenderer;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.loaders.ModelLoader;
import hr.avrbanac.openglplayground.loaders.OBJFileLoader;
import hr.avrbanac.openglplayground.models.TexturedModel;
import hr.avrbanac.openglplayground.renderers.MasterRenderer;
import hr.avrbanac.openglplayground.loaders.OBJSimpleLoader;
import hr.avrbanac.openglplayground.maths.Vector2f;
import hr.avrbanac.openglplayground.models.ModelData;
import hr.avrbanac.openglplayground.terrains.Terrain;
import hr.avrbanac.openglplayground.textures.ModelTexture;
import hr.avrbanac.openglplayground.textures.TerrainTexture;
import hr.avrbanac.openglplayground.textures.TerrainTexturePack;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWErrorCallback;

/**
 * Main class with application point of entry.
 * 
 * @author avrbanac
 * @version 1.0.8
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
        
        
//        TexturedModel tree = new TexturedModel(
//                OBJSimpleLoader.loadObjModel("tree", loader),
//                new ModelTexture(loader.loadTexture("tree")));
//        tree.getTexture().setShineDamper(10);
        TexturedModel grass = new TexturedModel(
                OBJSimpleLoader.loadObjModel("grassModel", loader),
                new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setTransparency(true);
        grass.getTexture().setShineDamper(10);
        grass.getTexture().setUseFakeLighting(true);
        
        TexturedModel flower = new TexturedModel(
                OBJSimpleLoader.loadObjModel("grassModel", loader),
                new ModelTexture(loader.loadTexture("flower")));
        flower.getTexture().setTransparency(true);
        flower.getTexture().setShineDamper(10);
        flower.getTexture().setUseFakeLighting(true);
        
        TexturedModel fern = new TexturedModel(
                OBJSimpleLoader.loadObjModel("fern", loader),
                new ModelTexture(loader.loadTexture("fernAtlas")));
        fern.getTexture().setTransparency(true);
        fern.getTexture().setShineDamper(10);
        // texture atlas 2x2
        fern.getTexture().setNumberOfRows(2);
        
        TexturedModel bunny = new TexturedModel(
                OBJSimpleLoader.loadObjModel("bunny", loader),
                new ModelTexture(loader.loadTexture("white")));
        
        TexturedModel lamp = new TexturedModel(
                OBJSimpleLoader.loadObjModel("lamp", loader),
                new ModelTexture(loader.loadTexture("lamp")));
        lamp.getTexture().setUseFakeLighting(true);

//        ModelTexture texture = tree.getTexture();
//        texture.setShineDamper(10);
//        texture.setReflectivity(1);
        
        //Entity entity = new Entity(staticModel, new Vector3f(0, 0, -25), 0, 0, 0, 1);

        List<Light> lights = new ArrayList<>();
        // "sun" with no attenuation; lower brightness
        lights.add(new Light(new Vector3f(0,1000, -7000), new Vector3f(0.4f, 0.4f, 0.4f)));
        
        lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f)));
        
        
        
        Player player = new Player(bunny, new Vector3f(200,0,-200), 0, 180, 0, 0.3f);
        
        Camera camera = new Camera(player);

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        
        Terrain terrain1 = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
        //Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap");
        
        Random random = new Random();
        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            entities.add(new Entity(
                    tree1, 
                    randomPos3f(random, terrain1),
                    0, 0, 0, 3));
            entities.add(new Entity(
                    tree2, 
                    randomPos3f(random, terrain1),
                    0, random.nextFloat() * 360, 0, 0.5f));
            entities.add(new Entity(
                    grass, 
                    randomPos3f(random, terrain1),
                    0, random.nextFloat() * 360, 0, 1));
            entities.add(new Entity(
                    fern,
                    random.nextInt(4),
                    randomPos3f(random, terrain1),
                    0, random.nextFloat() * 360, 0, 0.6f));
            entities.add(new Entity(
                    flower, 
                    randomPos3f(random, terrain1),
                    0, random.nextFloat() * 360, 0, 1f));
        }
        
        entities.add(new Entity(lamp, new Vector3f(184, -4.7f, -293), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(370, 4.2f, -300), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1));
        
        MasterRenderer renderer = new MasterRenderer(dm.getWidth(), dm.getHeight(), Globals.FOV);
        
        List<GuiTexture> guis = new ArrayList<>();
        GuiTexture gui = new GuiTexture(
                loader.loadTexture("carrot"),
                new Vector2f(0.75f, 0.75f),
                new Vector2f(0.20f, 0.20f));
        guis.add(gui);
        
        GuiRenderer guiRenderer = new GuiRenderer(loader);
        
        while(isRunning) {
            //entity.increasePosition(0, 0, -0.1f);
            //entity.increaseRotation(0, 1, 0);
            camera.move();
            
            // calculate which terrain player is standing on
            player.move(terrain1);
            
            renderer.processEntity(player);
            renderer.processTerrain(terrain1);
            //renderer.processTerrain(terrain2);
            for (int i = 0; i < 2503; i++) {
                renderer.processEntity(entities.get(i));
            }
            //renderer.processEntity(entity);
            
            renderer.render(lights, camera);
            guiRenderer.render(guis);
            
            dm.renderDisplay();
            dm.updateDisplay();
            
            // escape key pressed or close window button
            if(glfwWindowShouldClose(dm.getWindow())) {
                isRunning = false;
            }
        }
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

