
package hr.avrbanac.openglplayground.renderers;

import static hr.avrbanac.openglplayground.Globals.SKYBOX_SIZE;
import hr.avrbanac.openglplayground.display.DisplayManager;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.loaders.ModelLoader;
import hr.avrbanac.openglplayground.maths.Matrix4f;
import hr.avrbanac.openglplayground.models.RawModel;
import hr.avrbanac.openglplayground.shaders.SkyboxShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author avrbanac
 * @version 1.0.14
 */
public class SkyboxRenderer {
    private static final float[] VERTICES = {        
        -SKYBOX_SIZE,  SKYBOX_SIZE, -SKYBOX_SIZE,
        -SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE,
         SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE,
         SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE,
         SKYBOX_SIZE,  SKYBOX_SIZE, -SKYBOX_SIZE,
        -SKYBOX_SIZE,  SKYBOX_SIZE, -SKYBOX_SIZE,

        -SKYBOX_SIZE, -SKYBOX_SIZE,  SKYBOX_SIZE,
        -SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE,
        -SKYBOX_SIZE,  SKYBOX_SIZE, -SKYBOX_SIZE,
        -SKYBOX_SIZE,  SKYBOX_SIZE, -SKYBOX_SIZE,
        -SKYBOX_SIZE,  SKYBOX_SIZE,  SKYBOX_SIZE,
        -SKYBOX_SIZE, -SKYBOX_SIZE,  SKYBOX_SIZE,

         SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE,
         SKYBOX_SIZE, -SKYBOX_SIZE,  SKYBOX_SIZE,
         SKYBOX_SIZE,  SKYBOX_SIZE,  SKYBOX_SIZE,
         SKYBOX_SIZE,  SKYBOX_SIZE,  SKYBOX_SIZE,
         SKYBOX_SIZE,  SKYBOX_SIZE, -SKYBOX_SIZE,
         SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE,

        -SKYBOX_SIZE, -SKYBOX_SIZE,  SKYBOX_SIZE,
        -SKYBOX_SIZE,  SKYBOX_SIZE,  SKYBOX_SIZE,
         SKYBOX_SIZE,  SKYBOX_SIZE,  SKYBOX_SIZE,
         SKYBOX_SIZE,  SKYBOX_SIZE,  SKYBOX_SIZE,
         SKYBOX_SIZE, -SKYBOX_SIZE,  SKYBOX_SIZE,
        -SKYBOX_SIZE, -SKYBOX_SIZE,  SKYBOX_SIZE,

        -SKYBOX_SIZE,  SKYBOX_SIZE, -SKYBOX_SIZE,
         SKYBOX_SIZE,  SKYBOX_SIZE, -SKYBOX_SIZE,
         SKYBOX_SIZE,  SKYBOX_SIZE,  SKYBOX_SIZE,
         SKYBOX_SIZE,  SKYBOX_SIZE,  SKYBOX_SIZE,
        -SKYBOX_SIZE,  SKYBOX_SIZE,  SKYBOX_SIZE,
        -SKYBOX_SIZE,  SKYBOX_SIZE, -SKYBOX_SIZE,

        -SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE,
        -SKYBOX_SIZE, -SKYBOX_SIZE,  SKYBOX_SIZE,
         SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE,
         SKYBOX_SIZE, -SKYBOX_SIZE, -SKYBOX_SIZE,
        -SKYBOX_SIZE, -SKYBOX_SIZE,  SKYBOX_SIZE,
         SKYBOX_SIZE, -SKYBOX_SIZE,  SKYBOX_SIZE
    };
    private static final String[] DAY_TEXTURE_FILES = {
        "dayRight",
        "dayLeft",
        "dayTop",
        "dayBottom",
        "dayBack",
        "dayFront"
    };
    private static final String[] NIGHT_TEXTURE_FILES = {
        "nightRight",
        "nightLeft",
        "nightTop",
        "nightBottom",
        "nightBack",
        "nightFront"
    };
    
    private final RawModel cube;
    private final int dayTexture;
    private final int nightTexture;
    private final SkyboxShader shader;
    private float time = 0;
    
    public SkyboxRenderer(ModelLoader loader, Matrix4f projectionMatrix) {
        cube = loader.loadToVAO(VERTICES, 3);
        dayTexture = loader.loadCubeMap(DAY_TEXTURE_FILES);
        nightTexture = loader.loadCubeMap(NIGHT_TEXTURE_FILES);
        shader = new SkyboxShader();
        shader.start();
        shader.connectTextureUnits();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
    
    public void render(Camera camera, float r, float g, float b) {
        shader.start();
        shader.loadViewMatrix(camera);
        shader.loadFogColor(r, g, b);
        GL30.glBindVertexArray(cube.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        bindTextures();
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }
    
    private void bindTextures(){
        // day / night cycle calculation
        time += DisplayManager.getFrameTimeSeconds() * 1000;
        time %= 24000;
        int texture0;
        int texture1;
        float blendFactor;		
        if(time >= 0 && time < 5000){
            texture0 = nightTexture;
            texture1 = nightTexture;
            blendFactor = (time - 0)/(5000 - 0);
        }else if(time >= 5000 && time < 8000){
            texture0 = nightTexture;
            texture1 = dayTexture;
            blendFactor = (time - 5000)/(8000 - 5000);
        }else if(time >= 8000 && time < 21000){
            texture0 = dayTexture;
            texture1 = dayTexture;
            blendFactor = (time - 8000)/(21000 - 8000);
        }else{
            texture0 = dayTexture;
            texture1 = nightTexture;
            blendFactor = (time - 21000)/(24000 - 21000);
        }

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture0);
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
        shader.loadBlendFactor(blendFactor);
    }
}
