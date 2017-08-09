
package hr.avrbanac.openglplayground.renderers;


import hr.avrbanac.openglplayground.textures.GuiTexture;
import hr.avrbanac.openglplayground.loaders.ModelLoader;
import hr.avrbanac.openglplayground.maths.Matrix4f;
import hr.avrbanac.openglplayground.models.RawModel;
import hr.avrbanac.openglplayground.shaders.GuiShader;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author avrbanac
 * @version 1.0.9
 */
public class GuiRenderer {
    
    private final RawModel quad;
    private final GuiShader shader;
    
    public GuiRenderer(ModelLoader loader) {
        float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
        quad = loader.loadToVAO(positions, 2);
        shader = new GuiShader();
    }
    
    public void render(List<GuiTexture> guis) {
        shader.start();
        // bind model of the quad, all guis will be rendered to same quad
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        
        // for "transparency" GUI
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        // disable depth test for guis one over the other
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        
        // rendering
        for(GuiTexture gui : guis) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
            Matrix4f matrix = Matrix4f.transformation(gui.getPosition(), gui.getScale());
            shader.loadTransformation(matrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }
        
        // turn off blending and turn on depth test again
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        
        // unbind
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }
    
    public void cleanUp() {
        shader.cleanUp();
    }
}
