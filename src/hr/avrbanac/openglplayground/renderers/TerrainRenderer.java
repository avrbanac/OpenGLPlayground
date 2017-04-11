
package hr.avrbanac.openglplayground.renderers;

import hr.avrbanac.openglplayground.maths.Matrix4f;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.models.RawModel;
import hr.avrbanac.openglplayground.shaders.TerrainShader;
import hr.avrbanac.openglplayground.terrains.Terrain;
import hr.avrbanac.openglplayground.textures.ModelTexture;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Terrain renderer class.
 * 
 * @author avrbanac
 * @version 1.0.0
 */
public class TerrainRenderer {
    private TerrainShader shader;
    
    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
    
    public void render(List<Terrain> terrains) {
        for(Terrain terrain : terrains) {
            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            unbindTerrain();
        }
    }
    
    public void prepareTerrain(Terrain terrain) {
        RawModel rModel = terrain.getModel();
        GL30.glBindVertexArray(rModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        
        ModelTexture texture = terrain.getTexture();
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
    }
    
    public void unbindTerrain() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
    
    public void loadModelMatrix(Terrain terrain) {
        Matrix4f transformationMatrix = Matrix4f.transformation(
                new Vector3f(terrain.getX(), 0, terrain.getZ()),
                0,
                0,
                0,
                1);
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
