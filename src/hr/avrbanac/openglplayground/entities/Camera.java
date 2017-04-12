
package hr.avrbanac.openglplayground.entities;

import hr.avrbanac.openglplayground.maths.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Camera representation class.
 * 
 * @author avrbanac
 * @version 1.0.6
 */
public class Camera {
    
    private Vector3f position =  new Vector3f(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;
    private long parentWindowID;
    
    public Camera(long parentWindowID) {
        this.parentWindowID = parentWindowID;
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
        if(glfwGetKey(parentWindowID, GLFW_KEY_KP_8) == GLFW_PRESS) position.z-=0.2f;
        if(glfwGetKey(parentWindowID, GLFW_KEY_KP_2) == GLFW_PRESS) position.z+=0.2f;
        if(glfwGetKey(parentWindowID, GLFW_KEY_KP_6) == GLFW_PRESS) position.x+=0.2f;
        if(glfwGetKey(parentWindowID, GLFW_KEY_KP_4) == GLFW_PRESS) position.x-=0.2f;
        if(glfwGetKey(parentWindowID, GLFW_KEY_KP_7) == GLFW_PRESS) position.y+=0.2f;
        if(glfwGetKey(parentWindowID, GLFW_KEY_KP_1) == GLFW_PRESS) position.y-=0.2f;
        if(glfwGetKey(parentWindowID, GLFW_KEY_KP_9) == GLFW_PRESS) pitch+=0.2f;
        if(glfwGetKey(parentWindowID, GLFW_KEY_KP_3) == GLFW_PRESS) pitch-=0.2f;
        
    }
    
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    
}
