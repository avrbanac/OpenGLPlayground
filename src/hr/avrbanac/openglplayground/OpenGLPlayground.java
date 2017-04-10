
package hr.avrbanac.openglplayground;

import hr.avrbanac.openglplayground.entities.Entity;
import hr.avrbanac.openglplayground.display.DisplayManager;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.entities.Light;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.renderengine.ModelLoader;
import hr.avrbanac.openglplayground.renderengine.EntityRenderer;
import hr.avrbanac.openglplayground.models.RawModel;
import hr.avrbanac.openglplayground.models.TexturedModel;
import hr.avrbanac.openglplayground.renderengine.MasterRenderer;
import hr.avrbanac.openglplayground.renderengine.OBJLoader;
import hr.avrbanac.openglplayground.shaders.StaticShader;
import hr.avrbanac.openglplayground.terrains.Terrain;
import hr.avrbanac.openglplayground.textures.ModelTexture;
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
 * @version 1.0.3
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
        
        ModelLoader loader      = new ModelLoader();
        RawModel model          = OBJLoader.loadObjModel("tree", loader);
        
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        
        Random random = new Random();
        List<Entity> entityList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            entityList.add(new Entity(staticModel, new Vector3f(random.nextFloat() * 100 - 50, 0, random.nextFloat() * -100), 0, random.nextFloat() * 100,0, 1));
        }
        
        //Entity entity = new Entity(staticModel, new Vector3f(0, 0, -25), 0, 0, 0, 1);

        Light light = new Light(new Vector3f(3000,2000, 2000), new Vector3f(1,1,1));
        Camera camera = new Camera(dm.getWindow());
        camera.setPosition(new Vector3f(0,3,0));

        Terrain terrain1 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass")));

        
        MasterRenderer renderer = new MasterRenderer(dm.getWidth(), dm.getHeight(), Globals.FOV);
        
        while(isRunning) {
            //entity.increasePosition(0, 0, -0.1f);
            //entity.increaseRotation(0, 1, 0);
            
            camera.move();

            renderer.processTerrain(terrain1);
            renderer.processTerrain(terrain2);
            for (int i = 0; i < 200; i++) {
                renderer.processEntity(entityList.get(i));
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

