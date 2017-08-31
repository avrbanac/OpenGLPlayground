
package hr.avrbanac.openglplayground.shaders;

import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.entities.Light;
import hr.avrbanac.openglplayground.maths.Matrix4f;

/**
 *
 * @author avrbanac
 * @version 1.0.17
 */
public class WaterShader extends ShaderProgram {
    private int locationModelMatrix;
    private int locationViewMatrix;
    private int locationProjectionMatrix;
    private int locationReflectionTexutre;
    private int locationRefractionTexutre;
    private int locationDuDvMap;
    private int locationMoveFactor;
    private int locationCameraPosition;
    private int locationNormalMap;
    private int locationLightPosition;
    private int locationLightColor;
    private int locationDepthMap;
    private int locationNear;
    private int locationFar;
    
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
        locationCameraPosition      = getUniformLocation("cameraPosition");
        locationNormalMap           = getUniformLocation("normalMap");
        locationLightPosition       = getUniformLocation("lightPosition");
        locationLightColor          = getUniformLocation("lightColor");
        locationDepthMap            = getUniformLocation("depthMap");
        locationNear                = getUniformLocation("near");
        locationFar                 = getUniformLocation("far");
    }
    
    public void loadNearFarPlane() {
        super.loadFloat(locationNear, NEAR_PLANE);
        super.loadFloat(locationFar, FAR_PLANE);
    }
    
    public void loadMoveFactorOffset(float factor) {
        super.loadFloat(locationMoveFactor, factor);
    }
    
    public void connectTextureUnits() {
        super.loadInt(locationReflectionTexutre, 0);
        super.loadInt(locationRefractionTexutre, 1);
        super.loadInt(locationDuDvMap, 2);
        super.loadInt(locationNormalMap, 3);
        super.loadInt(locationDepthMap, 4);
    }
 
    public void loadProjectionMatrix(Matrix4f projection) {
        loadMatrix(locationProjectionMatrix, projection);
    }
     
    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Matrix4f.view(camera);
        loadMatrix(locationViewMatrix, viewMatrix);
        super.load3DVector(locationCameraPosition, camera.getPosition());
    }
 
    public void loadModelMatrix(Matrix4f modelMatrix){
        loadMatrix(locationModelMatrix, modelMatrix);
    }
    
    public void loadLight(Light sun) {
        super.load3DVector(locationLightPosition, sun.getPosition());
        super.load3DVector(locationLightColor, sun.getColor());
    }
 
}
