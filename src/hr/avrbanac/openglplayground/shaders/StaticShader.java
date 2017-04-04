
package hr.avrbanac.openglplayground.shaders;

import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.maths.Matrix4f;

/**
 * This is just an implementation of abstract class {@ShaderProgram}
 * 
 * @author avrbanac
 * @version 1.0.0
 */
public class StaticShader extends ShaderProgram {

    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;
    
    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
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
}
