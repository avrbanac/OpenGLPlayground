
package hr.avrbanac.openglplayground.shaders;

import static hr.avrbanac.openglplayground.Globals.GUI_VERTEX_FILE;
import static hr.avrbanac.openglplayground.Globals.GUI_FRAGMENT_FILE;
import hr.avrbanac.openglplayground.maths.Matrix4f;

/**
 *
 * @author avrbanac
 * @verion 1.0.9
 */
public class GuiShader extends ShaderProgram{
     
    private int location_transformationMatrix;
 
    public GuiShader() {
        super(GUI_VERTEX_FILE, GUI_FRAGMENT_FILE);
    }
     
    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }
 
    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
     
}
