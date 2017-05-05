
package hr.avrbanac.openglplayground.entities;

import hr.avrbanac.openglplayground.Globals;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.models.TexturedModel;

/**
 * Entity lamp object that will also hold light information.
 * 
 * @author avrbanac
 * @version 1.0.13
 */
public class LampStand extends Entity {
    
    private Light lampLight;
    private float lightHeightOffset;

    public LampStand(
            TexturedModel model,
            Vector3f position,
            float rotX, float rotY, float rotZ,
            float scale,
            Vector3f color) {
        
        this(model, 0, position, rotX, rotY, rotZ, scale,
                color,
                new Vector3f(Globals.L_ATTENUATION_1, Globals.L_ATTENUATION_2, Globals.L_ATTENUATION_3),
                Globals.LIGHT_HEIGHT_OFFSET);
    }
    
    public LampStand(
            TexturedModel model,
            Vector3f position,
            float rotX, float rotY, float rotZ,
            float scale,
            Vector3f color,
            float lightHeightOffset) {
        
        this(model, 0, position, rotX, rotY, rotZ, scale,
                color,
                new Vector3f(Globals.L_ATTENUATION_1, Globals.L_ATTENUATION_2, Globals.L_ATTENUATION_3),
                lightHeightOffset);  
    }
    
    public LampStand(
            TexturedModel model,
            Vector3f position,
            float rotX, float rotY, float rotZ,
            float scale,
            Vector3f color, Vector3f attenuation) {
        
        this(model, 0, position, rotX, rotY, rotZ, scale,
                color, attenuation, Globals.LIGHT_HEIGHT_OFFSET);
    }
    
    public LampStand(
            TexturedModel model,
            Vector3f position,
            float rotX, float rotY, float rotZ,
            float scale,
            Vector3f color, Vector3f attenuation, float lightHeightOffset) {
        
        this(model, 0, position, rotX, rotY, rotZ, scale,
                color, attenuation, lightHeightOffset);
    }
    
    public LampStand(
            TexturedModel model,
            int textureIndex,
            Vector3f position,
            float rotX, float rotY, float rotZ,
            float scale,
            Vector3f color) {
        
        this(model, textureIndex, position, rotX, rotY, rotZ, scale,
                color,
                new Vector3f(Globals.L_ATTENUATION_1, Globals.L_ATTENUATION_2, Globals.L_ATTENUATION_3),
                Globals.LIGHT_HEIGHT_OFFSET);
    }
    
    public LampStand(
            TexturedModel model,
            int textureIndex,
            Vector3f position,
            float rotX, float rotY, float rotZ,
            float scale,
            Vector3f color,
            float lightHeightOffset) {
        
        this(model, textureIndex, position, rotX, rotY, rotZ, scale,
                color,
                new Vector3f(Globals.L_ATTENUATION_1, Globals.L_ATTENUATION_2, Globals.L_ATTENUATION_3),
                lightHeightOffset);
    }
    
    public LampStand(
            TexturedModel model,
            int textureIndex,
            Vector3f position,
            float rotX, float rotY, float rotZ,
            float scale,
            Vector3f color, Vector3f attenuation) {
        
        this(model, textureIndex, position, rotX, rotY, rotZ, scale,
                color, attenuation, Globals.LIGHT_HEIGHT_OFFSET);
    }   
    
    public LampStand(
            TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale,
            Vector3f color, Vector3f attenuation, float lightHeightOffset) {
        
        super(model, textureIndex, position, rotX, rotY, rotZ, scale);
        
        this.lightHeightOffset = lightHeightOffset;
        this.lampLight = new Light(
                new Vector3f(position.x, position.y + (lightHeightOffset * scale), position.z),
                color, attenuation);
        
        // make normals face upwards for fake lightning since this is light source entity
        model.getTexture().setUseFakeLighting(true);
    }

    public Light getLampLight() {
        return lampLight;
    }

    public float getLightHeightOffset() {
        return lightHeightOffset;
    }

    public void setLightHeightOffset(float lightHeightOffset) {
        this.lightHeightOffset = lightHeightOffset;
    }

    @Override
    public void increaseRotation(float drx, float dry, float drz) {
        // lightStand can just rotate around y coordinate
        super.increaseRotation(0, dry, 0);
    }

    @Override
    public void increasePosition(float dx, float dy, float dz) {
        super.increasePosition(dx, dy, dz);
        Vector3f lightPosition = lampLight.getPosition();
        lightPosition.x += dx;
        lightPosition.y += dy;
        lightPosition.z += dz;
    }

    @Override
    public void setRotZ(float rotZ) {
        // lightStand can just rotate around y coordinate
        // super.setRotZ(rotZ);
    }

    @Override
    public void setRotY(float rotY) {
        super.setRotY(rotY); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setRotX(float rotX) {
        // lightStand can just rotate around y coordinate
        // super.setRotX(rotX);
    }

    @Override
    public void setPosition(Vector3f position) {
        super.setPosition(position);
        lampLight.setPosition(new Vector3f(position.x, position.y + (lightHeightOffset * super.getScale()), position.z));
    }

    @Override
    public void setScale(float scale) {
        super.setScale(scale);
        lampLight.setPosition(new Vector3f(super.getPosition().x, super.getPosition().y + (lightHeightOffset * scale), super.getPosition().z));
    }

    
}
