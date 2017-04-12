
package hr.avrbanac.openglplayground.entities;

import hr.avrbanac.openglplayground.maths.Vector3f;

/**
 * Light entity class.
 * 
 * @author avrbanac
 * @version 1.0.2
 */
public class Light {
    private Vector3f position;
    private Vector3f color;

    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
    
    
    
}
