
package hr.avrbanac.openglplayground.inputs;

import org.lwjgl.glfw.GLFWScrollCallback;

/**
 * Custom mouse scroll handler with callback triggered mouse scroll events.
 * 
 * @author avrbanac
 * @version 1.0.7
 */
public class MouseScrollHandler extends GLFWScrollCallback {

    private static int mouseScrollX, mouseScrollY;
    
    @Override
    public void invoke(long window, double xoffset, double yoffset) {
        mouseScrollX += (int)xoffset;
        mouseScrollY += (int)yoffset;
    }

    public static int getScrollX() {
        return mouseScrollX | (mouseScrollX = 0);
    }

    public static int getScrollY() {
        return mouseScrollY | (mouseScrollY = 0);
    }

}
