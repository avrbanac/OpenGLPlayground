
package hr.avrbanac.openglplayground.renderengine;

import hr.avrbanac.openglplayground.entities.Entity;
import hr.avrbanac.openglplayground.maths.Matrix4f;
import hr.avrbanac.openglplayground.models.RawModel;
import hr.avrbanac.openglplayground.models.TexturedModel;
import hr.avrbanac.openglplayground.shaders.StaticShader;
import hr.avrbanac.openglplayground.textures.ModelTexture;
import java.util.List;
import java.util.Map;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Renders model from VAO.
 * 
 * @author avrbanac
 * @version 1.0.3
 */
public class EntityRenderer {
    
    private StaticShader shader;
    
    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;

        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
    
    /**
     * New render model for rendering multiple models.
     * 
     * @param entities 
     */
    public void render (Map<TexturedModel, List<Entity>> entities) {
        for(TexturedModel model : entities.keySet()) {
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);
            for(Entity entity : batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }
    
    public void prepareTexturedModel(TexturedModel model) {
        RawModel rModel = model.getRawModel();
        GL30.glBindVertexArray(rModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        
        ModelTexture texture = model.getTexture();
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
    }
    
    public void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
    
    public void prepareInstance(Entity entity) {
        Matrix4f transformationMatrix = Matrix4f.transformation(
                entity.getPosition(),
                entity.getRotX(),
                entity.getRotY(),
                entity.getRotZ(),
                entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }
    
    /**
     * Old render method used for rendering single model.
     * 
     * @param entity
     * @param shader 
     */
    @Deprecated
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
        
        // deal with specular lightning
        ModelTexture texture = tModel.getTexture();
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        
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
