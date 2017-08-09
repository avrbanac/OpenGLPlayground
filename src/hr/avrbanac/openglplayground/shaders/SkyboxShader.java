
package hr.avrbanac.openglplayground.shaders;

import static hr.avrbanac.openglplayground.Globals.SKYBOX_FRAGMENT_FILE;
import static hr.avrbanac.openglplayground.Globals.SKYBOX_ROT_SPEED;
import static hr.avrbanac.openglplayground.Globals.SKYBOX_VERTEX_FILE;
import hr.avrbanac.openglplayground.display.DisplayManager;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.maths.Matrix4f;
import hr.avrbanac.openglplayground.maths.Vector3f;

/**
 *
 * @author avrbanac
 * @version 1.0.14
 */
public class SkyboxShader extends ShaderProgram {
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationFogColor;
    private int locationCubeMap0;
    private int locationCubeMap1;
    private int locationBlendFactor;
    
    
    private float rotation = 0;
     
    public SkyboxShader() {
        super(SKYBOX_VERTEX_FILE, SKYBOX_FRAGMENT_FILE);
    }
     
    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(locationProjectionMatrix, matrix);
    }
 
    public void loadViewMatrix(Camera camera){
        Matrix4f matrix = Matrix4f.view(camera);
        
        // fix so skybox will not move in relation to the camera
        matrix.elements[0 + 3 * 4] = 0;
        matrix.elements[1 + 3 * 4] = 0;
        matrix.elements[2 + 3 * 4] = 0;
        
        // use view matrix to rotate skybox (cloud movement)
        rotation += SKYBOX_ROT_SPEED * DisplayManager.getFrameTimeSeconds();
        matrix = matrix.multiply(Matrix4f.rotationY(rotation));
        
        super.loadMatrix(locationViewMatrix, matrix);
    }
    
    public void loadFogColor(float r, float g, float b) {
        super.load3DVector(locationFogColor, new Vector3f(r,g,b));
    }
    
    public void connectTextureUnits() {
        super.loadInt(locationCubeMap0, 0);
        super.loadInt(locationCubeMap1, 1);
    }
    
    public void loadBlendFactor(float blend) {
        super.loadFloat(locationBlendFactor, blend);
    }
     
    @Override
    protected void getAllUniformLocations() {
        locationProjectionMatrix    = super.getUniformLocation("projectionMatrix");
        locationViewMatrix          = super.getUniformLocation("viewMatrix");
        locationFogColor            = super.getUniformLocation("fogColor");
        locationCubeMap0            = super.getUniformLocation("cubeMap0");
        locationCubeMap1            = super.getUniformLocation("cubeMap1");
        locationBlendFactor         = super.getUniformLocation("blendFactor");
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
 
}