
package hr.avrbanac.openglplayground.maths;

/**
 * Custom Vector class (construct) to be used with LWJGL3.
 * 
 * @author avrbanac
 * @version 1.0.0
 */
public class Vector2f {
    
    public float u,v;
    
    public Vector2f() {
        u = 0.0f;
        v = 0.0f;
    }
    
    public Vector2f(float u, float v) {
        this.u = u;
        this.v = v;
    }
     
}

