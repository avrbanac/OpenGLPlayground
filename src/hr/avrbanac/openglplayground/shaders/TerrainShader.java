
package hr.avrbanac.openglplayground.shaders;

import static hr.avrbanac.openglplayground.Globals.TERRAIN_FRAGMENT_FILE;
import static hr.avrbanac.openglplayground.Globals.TERRAIN_VERTEX_FILE;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.entities.Light;
import hr.avrbanac.openglplayground.maths.Matrix4f;

/**
 * Terrain shader program.
 * 
 * @author avrbanac
 * @version 1.0.0
 */
public class TerrainShader extends ShaderProgram {
    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationLightPosition;
    private int locationLightColor;
    private int locationShineDamper;
    private int locationReflectivity;
    private int locationViewMatrixInverse;
    
    public TerrainShader() {
        super(TERRAIN_VERTEX_FILE, TERRAIN_FRAGMENT_FILE);
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
    
    // diffusal lightning
    public void loadLight(Light light) {
        super.loadVector(locationLightPosition, light.getPosition());
        super.loadVector(locationLightColor, light.getColor());
    }
    
    // specular lightning
    public void loadShineVariables(float shineDamper, float reflectivity) {
        super.loadFloat(locationShineDamper, shineDamper);
        super.loadFloat(locationReflectivity, reflectivity);
    }
}