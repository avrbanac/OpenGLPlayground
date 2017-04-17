/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.avrbanac.openglplayground.entities;

import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.display.DisplayManager;
import hr.avrbanac.openglplayground.inputs.KeyboardHandler;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.models.TexturedModel;
import hr.avrbanac.openglplayground.terrains.Terrain;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Player entity.
 * 
 * @author avrbanac
 * @version 1.0.8
 */
public class Player extends Entity {
    
    // for now
    private static final float TERRAIN_HEIGHT = 0;
    
    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardSpeed = 0;
    private boolean isInTheAir = false;
    private long keyCount = 0;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }
    
    public void move(Terrain terrain) {
        checkInputs();
        float frameTimeSeconds = DisplayManager.getFrameTimeSeconds();
                
        super.increaseRotation(0, currentTurnSpeed * frameTimeSeconds, 0);
        
        float distance = currentSpeed * frameTimeSeconds;
        float dx = (float)(distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float)(distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);
        
        upwardSpeed += GRAVITY * frameTimeSeconds;
        super.increasePosition(0, upwardSpeed * frameTimeSeconds, 0);
        
        // calculated terrein height
        float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        
        // stop pulling through terrain
        if(super.getPosition().y < terrainHeight) {
            upwardSpeed = 0;
            isInTheAir = false;
            super.getPosition().y = terrainHeight;
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
        if(KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
            currentSpeed = RUN_SPEED;
        } else if(KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
            currentSpeed = -RUN_SPEED;
        } else {
            currentSpeed = 0;
        }
        
        // turning left / right
        if(KeyboardHandler.isKeyDown(GLFW_KEY_A)) {
            currentTurnSpeed = TURN_SPEED;
        } else if(KeyboardHandler.isKeyDown(GLFW_KEY_D)) {
            currentTurnSpeed = -TURN_SPEED;
        } else {
            currentTurnSpeed = 0;
        }
        
        // jumping
        if(KeyboardHandler.isKeyDown(GLFW_KEY_SPACE)) {
            jump();
        }        
    }
    
}
