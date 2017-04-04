
package hr.avrbanac.openglplayground.models;

/**
 * This class represents 3D model in memory as a VAO.
 * 
 * @author avrbanac
 * @version 1.0.0
 */
public class RawModel {
    
    private int vaoID;
    private int vertexCount;
    
    public RawModel(int vaoID, int vertexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexCount() {
        return vertexCount;
    }
    
    
}
