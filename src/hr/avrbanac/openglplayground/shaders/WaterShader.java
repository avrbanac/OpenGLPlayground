
package hr.avrbanac.openglplayground.shaders;

import static hr.avrbanac.openglplayground.Globals.WATER_FRAGMENT_FILE;
import static hr.avrbanac.openglplayground.Globals.WATER_VERTEX_FILE;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.maths.Matrix4f;

/**
 *
 * @author avrbanac
 * @version 1.0.16
 */
public class WaterShader extends ShaderProgram {
    private int locationModelMatrix;
    private int locationViewMatrix;
    private int locationProjectionMatrix;
    private int locationReflectionTexutre;
    private int locationRefractionTexutre;
    private int locationDuDvMap;
    private int locationMoveFactor;
 
    public WaterShader() {
        super(WATER_VERTEX_FILE, WATER_FRAGMENT_FILE);
    }
 
    @Override
    protected void bindAttributes() {
        bindAttribute(0, "position");
    }
 
    @Override
    protected void getAllUniformLocations() {
        locationProjectionMatrix    = getUniformLocation("projectionMatrix");
        locationViewMatrix          = getUniformLocation("viewMatrix");
        locationModelMatrix         = getUniformLocation("modelMatrix");
        locationReflectionTexutre   = getUniformLocation("reflectionTexture");
        locationRefractionTexutre   = getUniformLocation("refractionTexture");
        locationDuDvMap             = getUniformLocation("dudvMap");
        locationMoveFactor          = getUniformLocation("moveFactorOffset");
    }
    
    public void loadMoveFactorOffset(float factor) {
        super.loadFloat(locationMoveFactor, factor);
    }
    
    public void connectTextureUnits() {
        super.loadInt(locationReflectionTexutre, 0);
        super.loadInt(locationRefractionTexutre, 1);
        super.loadInt(locationDuDvMap, 2);
    }
 
    public void loadProjectionMatrix(Matrix4f projection) {
        loadMatrix(locationProjectionMatrix, projection);
    }
     
    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Matrix4f.view(camera);
        loadMatrix(locationViewMatrix, viewMatrix);
    }
 
    public void loadModelMatrix(Matrix4f modelMatrix){
        loadMatrix(locationModelMatrix, modelMatrix);
    }
 
}
