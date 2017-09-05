
package hr.avrbanac.openglplayground.maths;

/**
 * Custom Vector class (construct) to be used with LWJGL3.
 * 
 * @author avrbanac
 * @version 1.0.18
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
    
    public Vector3f normalise() {
        return normalise(null);
    }
    
    public Vector3f normalise(Vector3f dest) {
        float l = length();

        if (dest == null) {
            dest = new Vector3f(x / l, y / l, z / l);
        } else {
            dest.set(x / l, y / l, z / l);
        }

        return dest;
    }
    
    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    
    public static Vector3f add(Vector3f left, Vector3f right, Vector3f dest) {
        return (dest == null)
                ? new Vector3f(left.x + right.x, left.y + right.y, left.z + right.z)
                : dest.set(left.x + right.x, left.y + right.y, left.z + right.z);
    }
    
    public static Vector3f sub(Vector3f left, Vector3f right, Vector3f dest) {
        return (dest == null)
                ? new Vector3f(left.x - right.x, left.y - right.y, left.z - right.z)
                : dest.set(left.x - right.x, left.y - right.y, left.z - right.z);
    }

    public Vector3f scale(float scale) {
        x *= scale;
        y *= scale;
        z *= scale;
        
        return this;
    }

    @Override
    public String toString() {
        return "Vector3f{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
    
}
