
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
    
}
