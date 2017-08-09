
package hr.avrbanac.openglplayground.textures;

/**
 * Terrain texture pack class.
 * 
 * @author avrbanac
 * @version 1.0.6
 */
public class TerrainTexturePack {
    private final TerrainTexture backgroundTexture;
    private final TerrainTexture rTexture;
    private final TerrainTexture gTexture;
    private final TerrainTexture bTexture;

    public TerrainTexturePack(TerrainTexture backgroundTexture, TerrainTexture rTexture, TerrainTexture gTexture, TerrainTexture bTexture) {
        this.backgroundTexture = backgroundTexture;
        this.rTexture = rTexture;
        this.gTexture = gTexture;
        this.bTexture = bTexture;
    }

    public TerrainTexture getBackgroundTexture() {
        return backgroundTexture;
    }

    public TerrainTexture getrTexture() {
        return rTexture;
    }

    public TerrainTexture getgTexture() {
        return gTexture;
    }

    public TerrainTexture getbTexture() {
        return bTexture;
    }
    
}
