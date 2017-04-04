
package hr.avrbanac.openglplayground.entities;

import hr.avrbanac.openglplayground.maths.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Camera representation class.
 * 
 * @author avrbanac
 * @version 1.0.0
 */
public class Camera {
    
    private Vector3f position =  new Vector3f(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;
    private long windowID;
    
    public Camera(long windowID) {
        this.windowID = windowID;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
    
    public void move() {
        if(glfwGetKey(windowID, GLFW_KEY_W) == GLFW_PRESS) position.z-=0.02f;
        if(glfwGetKey(windowID, GLFW_KEY_D) == GLFW_PRESS) position.x+=0.02f;
        if(glfwGetKey(windowID, GLFW_KEY_A) == GLFW_PRESS) position.x-=0.02f;
    }
    
}
