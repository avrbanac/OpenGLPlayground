
package hr.avrbanac.openglplayground;

import hr.avrbanac.openglplayground.entities.Entity;
import hr.avrbanac.openglplayground.display.DisplayManager;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.renderengine.ModelLoader;
import hr.avrbanac.openglplayground.renderengine.ModelRenderer;
import hr.avrbanac.openglplayground.models.RawModel;
import hr.avrbanac.openglplayground.models.TexturedModel;
import hr.avrbanac.openglplayground.renderengine.OBJLoader;
import hr.avrbanac.openglplayground.shaders.StaticShader;
import hr.avrbanac.openglplayground.textures.ModelTexture;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWErrorCallback;

/**
 * Main class with application point of entry.
 * 
 * @author avrbanac
 * @version 1.0.1
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
        StaticShader shader     = new StaticShader();
        ModelRenderer renderer  = new ModelRenderer(shader, dm.getWidth(), dm.getHeight());
        
        RawModel model          = OBJLoader.loadObjModel("Tree", loader);
        ModelTexture texture    = new ModelTexture(loader.loadTexture("metal"));
        
        TexturedModel texturedModel = new TexturedModel(model, texture);
        
        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-50), 0, 0, 0, 1);
        Camera camera = new Camera(dm.getWindow());
        
        while(isRunning) {
            //entity.increasePosition(0, 0, -0.1f);
            entity.increaseRotation(0, 1, 0);
            camera.move();
            
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();
            dm.renderDisplay();
            dm.updateDisplay();
            
            // escape key pressed or close window button
            if(glfwWindowShouldClose(dm.getWindow())) {
                isRunning = false;
            }
        }
        // clean up shaders
        shader.cleanUp();
        
        // clean up all the VAOs and VBOs
        loader.cleanUp();
        
        // close display
        dm.closeDisplay();
    }
    
}

