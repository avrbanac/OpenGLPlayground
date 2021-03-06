
package hr.avrbanac.openglplayground.display;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL;
import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.inputs.KeyboardHandler;
import hr.avrbanac.openglplayground.inputs.MouseButtonHandler;
import hr.avrbanac.openglplayground.inputs.MousePosHandler;
import hr.avrbanac.openglplayground.inputs.MouseScrollHandler;
import hr.avrbanac.openglplayground.utils.BufferUtils;
import java.nio.IntBuffer;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

/**
 * Custom manager for OpenGL display.
 *
 * @author avrbanac
 * @version 1.0.7
 */
public class DisplayManager {

    private long windowID = 0L;
    private int width = 0;
    private int height = 0;
    
    // time at the end of the last frame (in milliseconds)
    private static long lastFrameTime;
    
    // time taken to render the previous frame (in seconds)
    private static float delta;
    
    // input callbacks
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback mousePosCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWScrollCallback mouseScrollCallback;
    
    public void createDisplay() {
        // initialize display
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        // create window
        windowID = glfwCreateWindow(DISPLAY_WIDTH, DISPLAY_HEIGHT, DISPLAY_TITLE, NULL, NULL);
        if (windowID == NULL) {
            glfwTerminate();
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // links OpenGL context to the current thread
        glfwMakeContextCurrent(windowID);
        
        // cap the framerate to 60 (vsync)
        glfwSwapInterval(GLFW_TRUE);
        
        // create capabilities instance - current for the current thread (& OpenGL context)
        GL.createCapabilities();
        
        glfwShowWindow(windowID);
        
        IntBuffer w = BufferUtils.createIntBuffer(new int[1]);
        IntBuffer h = BufferUtils.createIntBuffer(new int[1]);
        glfwGetWindowSize(windowID, w, h);
        width = w.get(0);
        height = h.get(0);
        createInputCallbacks();
        
        lastFrameTime = getCurrentTime();
        
    }
    
    public void renderDisplay() {
        // checks for OpenGL errors
        int error = glGetError();
        if (error != GL_NO_ERROR) System.err.println(error);
        
        // moved to ModelRenderer#prepare
        //glClear(GL_COLOR_BUFFER_BIT);
        
        // Swaps out buffers - updates display
        glfwSwapBuffers(windowID);
    }
    
    public void updateDisplay() {
        // Polls for any window events such as the window closing etc.
        glfwPollEvents();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime) / 1000f;
        lastFrameTime = currentFrameTime;
    }
    
    public static float getFrameTimeSeconds() {
        return delta;
    }

    public void closeDisplay() {
        glfwDestroyWindow(windowID);
        destroyInputCallbacks();
        glfwTerminate();
    }

    public long getWindow() {
        return windowID;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    private static long getCurrentTime() {
        return (long)(glfwGetTime() * TO_MILLISECONDS);
    }
    
    private void createInputCallbacks() {
        glfwSetKeyCallback(windowID, keyCallback = new KeyboardHandler());
        glfwSetCursorPosCallback(windowID, mousePosCallback = new MousePosHandler());
        glfwSetMouseButtonCallback(windowID, mouseButtonCallback = new MouseButtonHandler());
        glfwSetScrollCallback(windowID, mouseScrollCallback = new MouseScrollHandler());
    }
    
    private void destroyInputCallbacks() {
        keyCallback.free();
        mousePosCallback.free();
        mouseButtonCallback.free();
        mouseScrollCallback.free();
    }

}
