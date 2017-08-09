
package hr.avrbanac.openglplayground.textures;

import hr.avrbanac.openglplayground.maths.Vector2f;

/**
 * 
 * @author avrbanac
 * @version 1.0.9
 */
public class GuiTexture {
    private final int texture;
    private final Vector2f position;
    private final Vector2f scale;

    public GuiTexture(int texture, Vector2f position, Vector2f scale) {
        this.texture = texture;
        this.position = position;
        this.scale = scale;
    }

    public int getTexture() {
        return texture;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }
    
}
