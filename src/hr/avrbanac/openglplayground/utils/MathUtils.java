
package hr.avrbanac.openglplayground.utils;

import hr.avrbanac.openglplayground.maths.Vector2f;
import hr.avrbanac.openglplayground.maths.Vector3f;

/**
 * Maths helper utility class.
 * 
 * @author avrbanac
 * @version 1.0.8
 */
public class MathUtils {
    public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.u - p3.x) + (p3.x - p2.x) * (pos.v - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.u - p3.x) + (p1.x - p3.x) * (pos.v - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }
    
}
