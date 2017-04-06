
package hr.avrbanac.openglplayground.renderengine;

import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.maths.Vector2f;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.models.RawModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Loader for OBJ models.
 * 
 * @author avrbanac
 * @version 1.0.0
 */
public class OBJLoader {
    public static RawModel loadObjModel(String fileName, ModelLoader loader) {
        FileReader fr = null;
        try {
            fr = new FileReader(new File(MODEL_FOLDER + fileName + MODEL_EXTENSION));
        } catch (FileNotFoundException e) {
            System.err.println("Could not load OBJ file!");
            e.printStackTrace();
        }
        
        BufferedReader reader = new BufferedReader(fr);
        String line;
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals  = new ArrayList<>();
        List<Integer> indices   = new ArrayList<>();
        
        float[] verticesArray   = null;
        float[] normalsArray    = null;
        float[] texturesArray   = null;
        int[] indicesArray      = null;
        
        try {
            while(true) {
                line = reader.readLine();
                String[] currentLine = line.split(" ");
            
                if(line.startsWith("v ")) {
                    // line is vertex definition
                    Vector3f vertex = new Vector3f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                } else if(line.startsWith("vt ")) {
                    // line is texture coordinate definition
                    Vector2f texture = new Vector2f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                } else if(line.startsWith("vn ")) {
                    // line is normal vector definition
                    Vector3f normal = new Vector3f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                } else if(line.startsWith("f ")) {
                    // line is face definition
                    texturesArray   = new float[vertices.size() * 2];
                    normalsArray    = new float[vertices.size() * 3];
                    break;
                }
            }
            
            // for face lines
            while(line != null) {
                if(!line.startsWith("f ")) {
                    line = reader.readLine();
                    continue;
                }
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");
                
                // process current triagle
                processVertex(vertex1, indices, textures, normals, texturesArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, texturesArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, texturesArray, normalsArray);
                
                line = reader.readLine();
            }
            reader.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];
        
        int vertexPointer = 0;
        for(Vector3f vertex : vertices) {
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }
        
        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }
        
        return loader.loadToVAO(verticesArray, texturesArray, indicesArray);
    }
    
    private static void processVertex(
            String[] vertexData,
            List<Integer> indices,
            List<Vector2f> textures,
            List<Vector3f> normals,
            float[] texturesArray,
            float[] normalsArray) {
        
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        
        Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
        texturesArray[currentVertexPointer * 2] = currentTexture.u;
        texturesArray[currentVertexPointer * 2 + 1] = 1 - currentTexture.v; // blender obj starts with bottom left so 1 - ....
        
        Vector3f currentNormal = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNormal.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNormal.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNormal.z;
    } 
    
    
}
