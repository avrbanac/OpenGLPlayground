
package hr.avrbanac.openglplayground.entities;

import hr.avrbanac.openglplayground.maths.Vector3f;

/**
 * Light entity class.
 * 
 * @author avrbanac
 * @version 1.0.12
 */
public class Light {
    private Vector3f position;
    private Vector3f color;
    
    // this is not really vector but it holds 3 values so...
    private Vector3f attenuation;

    
    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
        // default: no attenuation at all
        this.attenuation = new Vector3f(1,0,0);
    }
    
    public Light(Vector3f position, Vector3f color, Vector3f attenuation) {
        this.position = position;
        this.color = color;
        this.attenuation = attenuation;
    }
    
    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }
    
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public void setAttenuation(Vector3f attenuation) {
        this.attenuation = attenuation;
    }
    
    
}
