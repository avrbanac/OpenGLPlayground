
package hr.avrbanac.openglplayground.shaders;

import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.entities.Light;
import hr.avrbanac.openglplayground.maths.Matrix4f;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.maths.Vector4f;
import java.util.List;

/**
 * Terrain shader program.
 * 
 * @author avrbanac
 * @version 1.0.16
 */
public class TerrainShader extends ShaderProgram {
    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationLightPosition[];
    private int locationLightColor[];
    private int locationAttenuation[];
    private int locationShineDamper;
    private int locationReflectivity;
    private int locationViewMatrixInverse;
    private int locationSkyColor;
    private int locationbackgroundTexture;
    private int locationRTexture;
    private int locationGTexture;
    private int locationBTexture;
    private int locationBlendMap;
    private int locationClipPlane;
    
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
//        locationLightPosition           = super.getUniformLocation("lightPosition");
//        locationLightColor              = super.getUniformLocation("lightColor");
        locationShineDamper             = super.getUniformLocation("shineDamper");
        locationReflectivity            = super.getUniformLocation("reflectivity");
        locationSkyColor                = super.getUniformLocation("skyColor");
        locationbackgroundTexture       = super.getUniformLocation("backgroundTexture");
        locationRTexture                = super.getUniformLocation("rTexture");
        locationGTexture                = super.getUniformLocation("gTexture");
        locationBTexture                = super.getUniformLocation("bTexture");
        locationBlendMap                = super.getUniformLocation("blendMap");
        
        // clip plane
        locationClipPlane               = super.getUniformLocation("clipPlane");
        
        //this can be done in vertex shader using inverse of viewMatrix if GLSL version supports it
        locationViewMatrixInverse       = super.getUniformLocation("viewMatrixInv");
        
        // this part changed since we have arrays now instead of one light source
        locationLightPosition           = new int[MAX_LIGHTS_NUMBER];
        locationLightColor              = new int[MAX_LIGHTS_NUMBER];
        locationAttenuation             = new int[MAX_LIGHTS_NUMBER];
        for(int i = 0; i < MAX_LIGHTS_NUMBER; i++) {
            locationLightPosition[i]    = super.getUniformLocation("lightPosition[" + i + "]");
            locationLightColor[i]       = super.getUniformLocation("lightColor[" + i + "]");
            locationAttenuation[i]      = super.getUniformLocation("attenuation[" + i + "]");
        }
    }
    
    public void loadClipPlane(Vector4f clipPlane) {
        super.load4DVector(locationClipPlane, clipPlane);
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
    public void loadLights(List<Light> lights) {
        for (int i = 0; i < MAX_LIGHTS_NUMBER; i++) {
            if(i<lights.size()) {
                super.load3DVector(locationLightPosition[i], lights.get(i).getPosition());
                super.load3DVector(locationLightColor[i], lights.get(i).getColor());
                super.load3DVector(locationAttenuation[i], lights.get(i).getAttenuation());
            } else {
                // if there is not enough lights in list load up empty info
                super.load3DVector(locationLightPosition[i], new Vector3f(0,0,0));
                super.load3DVector(locationLightColor[i], new Vector3f(0,0,0));
                super.load3DVector(locationAttenuation[i], new Vector3f(1,0,0));
            }
        }
    }
    
    // specular lighting
    public void loadShineVariables(float shineDamper, float reflectivity) {
        super.loadFloat(locationShineDamper, shineDamper);
        super.loadFloat(locationReflectivity, reflectivity);
    }
    
    // sky color (for for etc...)
    public void loadSkyColor(float r, float g, float b) {
        super.load3DVector(locationSkyColor, new Vector3f(r,g,b));
    }
    
    // connect textures
    public void connectTextureUnits() {
        super.loadInt(locationbackgroundTexture, 0);
        super.loadInt(locationRTexture, 1);
        super.loadInt(locationGTexture, 2);
        super.loadInt(locationBTexture, 3);
        super.loadInt(locationBlendMap, 4);
    }
}
