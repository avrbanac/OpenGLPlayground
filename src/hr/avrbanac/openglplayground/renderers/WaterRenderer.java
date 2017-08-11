
package hr.avrbanac.openglplayground.renderers;

import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.display.DisplayManager;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.loaders.ModelLoader;
import hr.avrbanac.openglplayground.maths.Matrix4f;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.models.RawModel;
import hr.avrbanac.openglplayground.shaders.WaterShader;
import hr.avrbanac.openglplayground.surfaces.WaterTile;
import hr.avrbanac.openglplayground.utils.WaterFrameBuffers;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author avrbanac
 * @version 1.0.16
 */
public class WaterRenderer {
    private RawModel quad;
    private final WaterShader shader;
    private WaterFrameBuffers fbos;
    private final int dudvTexture;
    private float moveFactorOffset = 0;
 
    public WaterRenderer(ModelLoader loader, WaterShader shader, Matrix4f projectionMatrix, WaterFrameBuffers fbos) {
        this.shader = shader;
        this.fbos = fbos;
        this.dudvTexture = loader.loadTexture(DUDV_MAP);
        
        shader.start();
        shader.connectTextureUnits();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
        setUpVAO(loader);
    }
 
    public void render(List<WaterTile> water, Camera camera) {
        prepareRender(camera);  
        for (WaterTile tile : water) {
            Matrix4f modelMatrix = Matrix4f.transformation(
                    new Vector3f(tile.getX(), tile.getHeight(), tile.getZ()), 0, 0, 0,
                    WATER_TILE_SIZE);
            shader.loadModelMatrix(modelMatrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, quad.getVertexCount());
        }
        unbind();
    }
     
    private void prepareRender(Camera camera){
        shader.start();
        shader.loadViewMatrix(camera);
        moveFactorOffset += WATER_WAVE_SPEED * DisplayManager.getFrameTimeSeconds();
        moveFactorOffset %= 1;
        shader.loadMoveFactorOffset(moveFactorOffset);
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getReflectionTexture());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractionTexture());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, dudvTexture);
        
    }
     
    private void unbind(){
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }
 
    private void setUpVAO(ModelLoader loader) {
        // Just x and z vectex positions here, y is set to 0 in v.shader
        float[] vertices = { -1, -1, -1, 1, 1, -1, 1, -1, -1, 1, 1, 1 };
        quad = loader.loadToVAO(vertices, 2);
    }
}
