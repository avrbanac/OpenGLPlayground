
package hr.avrbanac.openglplayground.entities;

import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.inputs.MouseButtonHandler;
import hr.avrbanac.openglplayground.inputs.MousePosHandler;
import hr.avrbanac.openglplayground.inputs.MouseScrollHandler;
import hr.avrbanac.openglplayground.maths.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Camera representation class.
 * 
 * @author avrbanac
 * @version 1.0.7
 */
public class Camera {
    
    private Vector3f position;
    private float pitch;
    private float yaw;
    private float roll;
    
    private Player player;
    private float distanceFromPlayer;
    private float angleAroundPlayer;
    
    public Camera(Player player) {
        this.player = player;
        position    =  new Vector3f(0,0,0);
        pitch   = 20;
        yaw     = 0;
        roll    = 0;
        distanceFromPlayer  = 50;
        angleAroundPlayer   = 0;
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
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();
        
        calculateCameraPosition(calculateHorizontalDistance(), calculateVerticalDistance());
        
        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
    }
    
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    
    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta     = player.getRotY() + angleAroundPlayer;
        float offsetX   = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ   = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        
        position.x      = player.getPosition().x - offsetX;
        position.y      = player.getPosition().y + verticalDistance;
        position.z      = player.getPosition().z - offsetZ;
    }
    
    private void calculateZoom() {
        float zoomLevel = MouseScrollHandler.getScrollY() * MOUSE_SENS_SCROLL;
        distanceFromPlayer -= zoomLevel;
        if (distanceFromPlayer < CAMERA_MIN_DIST) distanceFromPlayer = CAMERA_MIN_DIST;
        if (distanceFromPlayer > CAMERA_MAX_DIST) distanceFromPlayer = CAMERA_MAX_DIST;
    }
    
    private void calculatePitch() {
        if(MouseButtonHandler.isButtonDown(GLFW_MOUSE_BUTTON_2)) {
            if (MouseButtonHandler.hasButtonChangedState(GLFW_MOUSE_BUTTON_2)) MousePosHandler.resetDPos();
            float pitchChange = MousePosHandler.getDY() * MOUSE_SENS_MOVE_Y;
            pitch -= pitchChange;
            if (pitch < CAMERA_MIN_PITCH) pitch = CAMERA_MIN_PITCH;
            if (pitch > CAMERA_MAX_PITCH) pitch = CAMERA_MAX_PITCH;
        }
    }
    
    private void calculateAngleAroundPlayer() {
        if(MouseButtonHandler.isButtonDown(GLFW_MOUSE_BUTTON_2)) {
            if (MouseButtonHandler.hasButtonChangedState(GLFW_MOUSE_BUTTON_2)) MousePosHandler.resetDPos();
            int dx = MousePosHandler.getDX();
            float angleChange = dx * MOUSE_SENS_MOVE_X;
            angleAroundPlayer -= angleChange;
            //System.out.println("DX=" + String.valueOf(dx) + " CHANGE="+ String.valueOf(angleChange) + " ANGLE=" + String.valueOf(angleAroundPlayer));
        }
    }
    
    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }
    
    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }
    
}
