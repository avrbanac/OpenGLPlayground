
package hr.avrbanac.openglplayground.maths;

/**
 * Custom Vector (4f) class (construct) to be used with LWJGL3.
 * 
 * @author avrbanac
 * @version 1.0.15
 */
public class Vector4f {
    public float x,y,z,w;
    
    public Vector4f() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
        w = 0.0f;
    }

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
}
