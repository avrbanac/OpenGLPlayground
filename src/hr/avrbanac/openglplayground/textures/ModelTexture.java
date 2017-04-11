
package hr.avrbanac.openglplayground.textures;

/**
 * Model texture definition class.
 * 
 * @author avrbanac
 * @version 1.0.3
 */
public class ModelTexture {
    private int textureID;
    
    private float shineDamper = 1;
    private float reflectivity = 0;
    
    // this is used for turning off face culling 
    private boolean transparency = false;
    
    // this is used for lighting simple terrain objects which use very small number of quad planes (normals)
    private boolean useFakeLighting = false;
    
    public ModelTexture(int textureID) {
        this.textureID = textureID;
    }
    
    public int getTextureID() {
        return textureID;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public boolean isTransparency() {
        return transparency;
    }

    public void setTransparency(boolean transparency) {
        this.transparency = transparency;
    }

    public boolean isUseFakeLighting() {
        return useFakeLighting;
    }

    public void setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }
    
    
}
