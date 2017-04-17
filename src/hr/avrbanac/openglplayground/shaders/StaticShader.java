
package hr.avrbanac.openglplayground.shaders;

import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.entities.Light;
import hr.avrbanac.openglplayground.maths.Matrix4f;
import hr.avrbanac.openglplayground.maths.Vector2f;
import hr.avrbanac.openglplayground.maths.Vector3f;

/**
 * This is just an implementation of abstract class {@ShaderProgram}
 * 
 * @author avrbanac
 * @version 1.0.8
 */
public class StaticShader extends ShaderProgram {

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationLightPosition;
    private int locationLightColor;
    private int locationShineDamper;
    private int locationReflectivity;
    private int locationViewMatrixInverse;
    private int locationUseFakeLighting;
    private int locationSkyColor;
    private int locationNumberOfRows;
    private int locationOffset;
    
    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix    = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix        = super.getUniformLocation("projectionMatrix");
        locationViewMatrix              = super.getUniformLocation("viewMatrix");
        locationLightPosition           = super.getUniformLocation("lightPosition");
        locationLightColor              = super.getUniformLocation("lightColor");
        locationShineDamper             = super.getUniformLocation("shineDamper");
        locationReflectivity            = super.getUniformLocation("reflectivity");
        locationUseFakeLighting         = super.getUniformLocation("useFakeLighting");
        locationSkyColor                = super.getUniformLocation("skyColor");
        locationNumberOfRows            = super.getUniformLocation("numberOfRows");
        locationOffset                  = super.getUniformLocation("offset");
        
        //this can be done in vertex shader using inverse of viewMatrix if GLSL version supports it
        locationViewMatrixInverse       = super.getUniformLocation("viewMatrixInv");
        
    }
    
    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(locationTransformationMatrix, matrix);
    }
    
    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Matrix4f.view(camera);
        Matrix4f viewMatrixInv = Matrix4f.inverse(viewMatrix);
        super.loadMatrix(locationViewMatrix, viewMatrix);
        super.loadMatrix(locationViewMatrixInverse, viewMatrixInv);
    }
    
    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(locationProjectionMatrix, matrix);
    }
    
    // diffusal lighting
    public void loadLight(Light light) {
        super.load3DVector(locationLightPosition, light.getPosition());
        super.load3DVector(locationLightColor, light.getColor());
    }
    
    // specular lighting
    public void loadShineVariables(float shineDamper, float reflectivity) {
        super.loadFloat(locationShineDamper, shineDamper);
        super.loadFloat(locationReflectivity, reflectivity);
    }
    
    // fake lighting
    public void loadFakeLightingVariable(boolean useFakeLighting) {
        super.loadBoolean(locationUseFakeLighting, useFakeLighting);
    }
    
    // sky color (can be changed)
    public void loadSkyColor(float r, float g, float b) {
        super.load3DVector(locationSkyColor, new Vector3f(r,g,b));
    }
    
    // variables needed for texture atlases
    public void loadNumberOfRows(int numberOfRows) {
        super.loadFloat(locationNumberOfRows, numberOfRows);
    }
    // variables needed for texture atlases
    public void loadOffset(float x, float y) {
        super.load2DVector(locationOffset, new Vector2f(x,y));
    }
}
