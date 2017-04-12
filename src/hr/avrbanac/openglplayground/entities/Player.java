/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.avrbanac.openglplayground.entities;

import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.display.DisplayManager;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.models.TexturedModel;
import static org.lwjgl.glfw.GLFW.*;

/**
 *
 * @author avrbanac
 */
public class Player extends Entity {
    
    // for now
    private static final float TERRAIN_HEIGHT = 0;
    
    private long parentWindowID;
    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardSpeed = 0;
    private boolean isInTheAir = false;

    public Player(long parentWindowID, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.parentWindowID = parentWindowID;
    }
    
    public void move() {
        checkInputs();
        float frameTimeSeconds = DisplayManager.getFrameTimeSeconds();
                
        super.increaseRotation(0, currentTurnSpeed * frameTimeSeconds, 0);
        
        float distance = currentSpeed * frameTimeSeconds;
        float dx = (float)(distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float)(distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        
        upwardSpeed += GRAVITY * frameTimeSeconds;
        super.increasePosition(0, upwardSpeed * frameTimeSeconds, 0);
        // stop pulling through terrain
        if(super.getPosition().y < TERRAIN_HEIGHT) {
            upwardSpeed = 0;
            isInTheAir = false;
            super.getPosition().y = TERRAIN_HEIGHT;
        }
    }
    
    private void jump() {
        if(!isInTheAir) {
            upwardSpeed = JUMP_POWER;
            isInTheAir = true;
        }
    }
    
    private void checkInputs() {
        // moving forward / backward
        if(glfwGetKey(parentWindowID, GLFW_KEY_W) == GLFW_PRESS) {
            currentSpeed = RUN_SPEED;
        } else if(glfwGetKey(parentWindowID, GLFW_KEY_S) == GLFW_PRESS) {
            currentSpeed = -RUN_SPEED;
        } else {
            currentSpeed = 0;
        }
        
        // turning left / right
        if(glfwGetKey(parentWindowID, GLFW_KEY_A) == GLFW_PRESS) {
            currentTurnSpeed = TURN_SPEED;
        } else if(glfwGetKey(parentWindowID, GLFW_KEY_D) == GLFW_PRESS) {
            currentTurnSpeed = -TURN_SPEED;
        } else {
            currentTurnSpeed = 0;
        }
        
        // jumping
        if(glfwGetKey(parentWindowID, GLFW_KEY_SPACE) == GLFW_PRESS) {
            jump();
        }        
    }
    
}
