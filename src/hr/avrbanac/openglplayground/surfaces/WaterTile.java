
package hr.avrbanac.openglplayground.surfaces;

/**
 *
 * @author avrbanac
 * @version 1.0.16
 */
public class WaterTile {
    private float height;
    private float x,z;
     
    public WaterTile(float centerX, float centerZ, float height){
        this.x = centerX;
        this.z = centerZ;
        this.height = height;
    }
 
    public float getHeight() {
        return height;
    }
 
    public float getX() {
        return x;
    }
 
    public float getZ() {
        return z;
    }
 
}
