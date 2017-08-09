
package hr.avrbanac.openglplayground.inputs;

import static hr.avrbanac.openglplayground.Globals.DISPLAY_HEIGHT;
import static hr.avrbanac.openglplayground.Globals.MOUSE_Y_INVERT;
import org.lwjgl.glfw.GLFWCursorPosCallback;



/**
 * Custom mouse position handler with callback triggered mouse movement events.
 * 
 * @author avrbanac
 * @version 1.0.7
 */
public class MousePosHandler extends GLFWCursorPosCallback {
    
    private static int mouseX, mouseY, mouseDX, mouseDY;
    
    public MousePosHandler() {
        MousePosHandler.mouseX = 0;
        MousePosHandler.mouseY = 0;
        MousePosHandler.mouseDX = 0;
        MousePosHandler.mouseDY = 0;
    }
    
    @Override
    public void invoke(long window, double xpos, double ypos) {
        int yCorrected = MOUSE_Y_INVERT ? (int)ypos : DISPLAY_HEIGHT - (int)ypos;
        
        mouseDX += (int)xpos - mouseX;
        mouseDY += yCorrected - mouseY;
        
        mouseX = (int)xpos;
        mouseY = yCorrected;
    }

    public static int getX() {
        return mouseX;
    }

    public static int getY() {
        return mouseY;
    }

    public static int getDX() {
        return mouseDX | (mouseDX = 0);
    }

    public static int getDY() {
        return mouseDY | (mouseDY = 0);
    }
    
    public static void resetDPos() {
        mouseDX = 0;
        mouseDY = 0;
    }
    
}
