
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

    private static boolean[] buttons = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];
    private static boolean[] buttonsChange = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];
    
    @Override
    public void invoke(long window, int button, int action, int mods) {
        boolean newState = action != GLFW_RELEASE;
        
        buttonsChange[button] = (buttons[button] != newState);
        buttons[button]= newState;
        
    }
    
    public static boolean isButtonDown(int buttonCode) {
        return buttons[buttonCode];
    }
    
    public static boolean hasButtonChangedState(int buttonCode) {
        return buttonsChange[buttonCode] | (buttonsChange[buttonCode] = false);
    }
    
}
