
package hr.avrbanac.openglplayground.inputs;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 * Custom mouse button handler with callback triggered mouse button events.
 * 
 * @author avrbanac
 * @version 1.0.7
 */
public class MouseButtonHandler extends GLFWMouseButtonCallback {

    private static final boolean[] BUTTONS = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];
    private static final boolean[] BUTTONS_CHANGE = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];
    
    @Override
    public void invoke(long window, int button, int action, int mods) {
        boolean newState = action != GLFW_RELEASE;
        
        BUTTONS_CHANGE[button] = (BUTTONS[button] != newState);
        BUTTONS[button]= newState;
        
    }
    
    public static boolean isButtonDown(int buttonCode) {
        return BUTTONS[buttonCode];
    }
    
    public static boolean hasButtonChangedState(int buttonCode) {
        return BUTTONS_CHANGE[buttonCode] | (BUTTONS_CHANGE[buttonCode] = false);
    }
    
}
