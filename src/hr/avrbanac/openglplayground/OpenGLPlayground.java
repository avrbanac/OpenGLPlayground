
package hr.avrbanac.openglplayground;

import hr.avrbanac.openglplayground.entities.Entity;
import hr.avrbanac.openglplayground.display.DisplayManager;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.entities.Light;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.loaders.ModelLoader;
import hr.avrbanac.openglplayground.loaders.OBJFileLoader;
import hr.avrbanac.openglplayground.models.TexturedModel;
import hr.avrbanac.openglplayground.renderers.MasterRenderer;
import hr.avrbanac.openglplayground.loaders.OBJSimpleLoader;
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
 * @version 1.0.6
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
                new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setTransparency(true);
        fern.getTexture().setShineDamper(10);

//        ModelTexture texture = tree.getTexture();
//        texture.setShineDamper(10);
//        texture.setReflectivity(1);
        
        Random random = new Random();
        List<Entity> entities = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            entities.add(new Entity(
                    tree1, 
                    new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600),
                    0, 0, 0, 3));
            entities.add(new Entity(
                    tree2, 
                    new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600),
                    0, random.nextFloat() * 360, 0, 0.5f));
            entities.add(new Entity(
                    grass, 
                    new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600),
                    0, random.nextFloat() * 360, 0, 1));
            entities.add(new Entity(
                    fern, 
                    new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600),
                    0, random.nextFloat() * 360, 0, 0.6f));
            entities.add(new Entity(
                    flower, 
                    new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600),
                    0, random.nextFloat() * 360, 0, 1f));
        }
        
        //Entity entity = new Entity(staticModel, new Vector3f(0, 0, -25), 0, 0, 0, 1);

        Light light = new Light(new Vector3f(3000,2000, 2000), new Vector3f(1,1,1));
        Camera camera = new Camera(dm.getWindow());
        camera.setPosition(new Vector3f(0,3,0));

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        
        Terrain terrain1 = new Terrain(0, -1, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);
        
        MasterRenderer renderer = new MasterRenderer(dm.getWidth(), dm.getHeight(), Globals.FOV);
        
        while(isRunning) {
            //entity.increasePosition(0, 0, -0.1f);
            //entity.increaseRotation(0, 1, 0);
            camera.move();

            renderer.processTerrain(terrain1);
            renderer.processTerrain(terrain2);
            for (int i = 0; i < 2500; i++) {
                renderer.processEntity(entities.get(i));
            }
            //renderer.processEntity(entity);
            
            renderer.render(light, camera);
            
            dm.renderDisplay();
            dm.updateDisplay();
            
            // escape key pressed or close window button
            if(glfwWindowShouldClose(dm.getWindow())) {
                isRunning = false;
            }
        }
        // clean up shaders
        renderer.cleanUp();
        
        // clean up all the VAOs and VBOs
        loader.cleanUp();
        
        // close display
        dm.closeDisplay();
    }
    
}

