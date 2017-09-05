
package hr.avrbanac.openglplayground.maths;

/**
 * Custom Vector class (construct) to be used with LWJGL3.
 * 
 * @author avrbanac
 * @version 1.0.18
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

    public float getU() {
        return u;
    }

    public float getV() {
        return v;
    }
    
    public Vector2f set(Vector2f src) {
        u = src.getU();
        v = src.getV();
        return this;
    }
    
    public Vector2f set(float u, float v) {
        this.u = u;
        this.v = v;
        return this;
    }
    
    public static Vector2f sub(Vector2f left, Vector2f right, Vector2f dest) {
        return (dest == null)
                ? new Vector2f(left.u - right.u, left.v - right.v)
                : dest.set(left.u - right.u, left.v - right.v);
    }
     
}

