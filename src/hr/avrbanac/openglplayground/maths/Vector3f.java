
package hr.avrbanac.openglplayground.maths;

/**
 * Custom Vector class (construct) to be used with LWJGL3.
 * 
 * @author avrbanac
 * @version 1.0.0
 */
public class Vector3f {
    
    public float x,y,z;
    
    public Vector3f() {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }
    
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }
     
}
