
package hr.avrbanac.openglplayground.models;

import hr.avrbanac.openglplayground.textures.ModelTexture;

/**
 * Textured raw model wrapper class.
 * 
 * @author avrbanac
 * @version 1.0.0
 */
public class TexturedModel {
    
    private final RawModel rawModel;
    private final ModelTexture texture;

    public TexturedModel(RawModel rawModel, ModelTexture texture) {
        this.rawModel = rawModel;
        this.texture = texture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }
    
    
}
