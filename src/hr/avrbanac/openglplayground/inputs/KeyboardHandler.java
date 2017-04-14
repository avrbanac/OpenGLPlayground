
package hr.avrbanac.openglplayground.inputs;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import org.lwjgl.glfw.GLFWKeyCallback;

/**
 * Custom keyboard handler with callback triggered keyboard events.
 * 
 * @author avrbanac
 * @version 1.0.7
 */
public class KeyboardHandler extends GLFWKeyCallback {

    private static boolean[] keys = new boolean[65536];
    
    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key]= action != GLFW_RELEASE;
    }
    
    public static boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }
   
}
