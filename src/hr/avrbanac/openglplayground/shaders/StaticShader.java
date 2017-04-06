
package hr.avrbanac.openglplayground.shaders;

import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.entities.Light;
import hr.avrbanac.openglplayground.maths.Matrix4f;

/**
 * This is just an implementation of abstract class {@ShaderProgram}
 * 
 * @author avrbanac
 * @version 1.0.2
 */
public class StaticShader extends ShaderProgram {

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationLightPosition;
    private int locationLightColor;
    
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
        
    }
    
    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(locationTransformationMatrix, matrix);
    }
    
    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Matrix4f.view(camera);
        super.loadMatrix(locationViewMatrix, viewMatrix);
    }
    
    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(locationProjectionMatrix, matrix);
    }
    
    public void loadLight(Light light) {
        super.loadVector(locationLightPosition, light.getPosition());
        super.loadVector(locationLightColor, light.getColor());
    }
}
