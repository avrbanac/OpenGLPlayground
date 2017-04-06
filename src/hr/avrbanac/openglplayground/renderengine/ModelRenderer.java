
package hr.avrbanac.openglplayground.renderengine;

import hr.avrbanac.openglplayground.entities.Entity;
import hr.avrbanac.openglplayground.maths.Matrix4f;
import hr.avrbanac.openglplayground.models.RawModel;
import hr.avrbanac.openglplayground.models.TexturedModel;
import hr.avrbanac.openglplayground.shaders.StaticShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Renders model from VAO.
 * 
 * @author avrbanac
 * @version 1.0.2
 */
public class ModelRenderer {
    
    private Matrix4f projectionMatrix;
    
    public ModelRenderer(StaticShader shader, int screenWidth, int screenHeight) {
        // we load projection matrix only once - so it's in the constructor
        projectionMatrix = Matrix4f.projection(screenWidth, screenHeight);
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
    
    public void prepare() {
        // test which triangles are "in front" of others
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        // clear the color from previous frame
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        // clear everything with background color (black in this case)
        GL11.glClearColor(0, 0, 0, 1);
    }
    
    public void render(Entity entity, StaticShader shader) {
        TexturedModel tModel = entity.getModel();
        RawModel rModel = tModel.getRawModel();
        
        // bind
        GL30.glBindVertexArray(rModel.getVaoID());
        
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        
        Matrix4f transformationMatrix = Matrix4f.transformation(
                entity.getPosition(),
                entity.getRotX(),
                entity.getRotY(),
                entity.getRotZ(),
                entity.getScale());
        
        shader.loadTransformationMatrix(transformationMatrix);
        
        // obsolite, we now use indices
        // GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, rModel.getVertexCount());
        
        // use first texture bank
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        
        // bind texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tModel.getTexture().getTextureID());
        
        GL11.glDrawElements(GL11.GL_TRIANGLES, rModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        
        // unbind
        GL30.glBindVertexArray(0);
    }
}
