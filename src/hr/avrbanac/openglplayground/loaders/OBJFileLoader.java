
package hr.avrbanac.openglplayground.loaders;

import static hr.avrbanac.openglplayground.Globals.MODEL_EXTENSION;
import static hr.avrbanac.openglplayground.Globals.MODEL_FOLDER;
import hr.avrbanac.openglplayground.maths.Vertex;
import hr.avrbanac.openglplayground.maths.Vector2f;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.models.ModelData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * New OBJ loader class. This class is more optimized than {@link OBJSimpleLoader}.
 * 
 * @author avrbanac
 * @version 1.0.1
 */
public class OBJFileLoader {

    public static ModelData loadOBJ(String objFileName) {
        FileReader isr  = null;
        File objFile    = new File(MODEL_FOLDER + objFileName + MODEL_EXTENSION);
        try {
            isr = new FileReader(objFile);
        } catch (FileNotFoundException e) {
            System.err.println("File not found in res folder. (Don't use any extention)");
        }
        BufferedReader reader   = new BufferedReader(isr);
        String line;
        List<Vertex> vertices   = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals  = new ArrayList<>();
        List<Integer> indices   = new ArrayList<>();
        try {
            // read to the first occurrence of the face definition
            while (true) {
                line = reader.readLine();
                if (line.startsWith("v ")) {
                    // line is vertex definition
                    String[] currentLine = line.split(" ");
                    Vector3f vertex = new Vector3f(
                            (float) Float.valueOf(currentLine[1]),
                            (float) Float.valueOf(currentLine[2]),
                            (float) Float.valueOf(currentLine[3]));
                    Vertex newVertex = new Vertex(vertices.size(), vertex);
                    vertices.add(newVertex);
                } else if (line.startsWith("vt ")) {
                    // line is texture coordinate definition
                    String[] currentLine = line.split(" ");
                    Vector2f texture = new Vector2f((float) Float.valueOf(currentLine[1]),
                            (float) Float.valueOf(currentLine[2]));
                    textures.add(texture);
                } else if (line.startsWith("vn ")) {
                    // line is normal vector definition
                    String[] currentLine = line.split(" ");
                    Vector3f normal = new Vector3f((float) Float.valueOf(currentLine[1]),
                            (float) Float.valueOf(currentLine[2]),
                            (float) Float.valueOf(currentLine[3]));
                    normals.add(normal);
                } else if (line.startsWith("f ")) {
                    // line is face definition, no more v, vt or vn lines expected: break!
                    break;
                }
            }
            // read through the rest of the file: lines are face definitions
            while (line != null && line.startsWith("f ")) {
                // 3 triplets are packed together into single line
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");
                
                processVertex(vertex1, vertices, indices);
                processVertex(vertex2, vertices, indices);
                processVertex(vertex3, vertices, indices);

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading the file");
        }
        removeUnusedVertices(vertices);
        float[] verticesArray   = new float[vertices.size() * 3];
        float[] texturesArray   = new float[vertices.size() * 2];
        float[] normalsArray    = new float[vertices.size() * 3];
        
        float furthest      = convertDataToArrays(vertices, textures, normals, verticesArray, texturesArray, normalsArray);
        int[] indicesArray  = convertIndicesListToArray(indices);
        ModelData data      = new ModelData(verticesArray, texturesArray, normalsArray, indicesArray, furthest);
        return data;
    }

    private static void processVertex(String[] vertex, List<Vertex> vertices, List<Integer> indices) {
        int index               = Integer.parseInt(vertex[0]) - 1;
        Vertex currentVertex    = vertices.get(index);
        int textureIndex        = Integer.parseInt(vertex[1]) - 1;
        int normalIndex         = Integer.parseInt(vertex[2]) - 1;
        if (!currentVertex.isSet()) {
            currentVertex.setTextureIndex(textureIndex);
            currentVertex.setNormalIndex(normalIndex);
            indices.add(index);
        } else {
            dealWithAlreadyProcessedVertex(currentVertex, textureIndex, normalIndex, indices, vertices);
        }
    }

    private static int[] convertIndicesListToArray(List<Integer> indices) {
        int[] indicesArray = new int[indices.size()];
        for (int i = 0; i < indicesArray.length; i++) {
                indicesArray[i] = indices.get(i);
        }
        return indicesArray;
    }

    private static float convertDataToArrays(
            List<Vertex> vertices,
            List<Vector2f> textures,
            List<Vector3f> normals,
            float[] verticesArray,
            float[] texturesArray,
            float[] normalsArray) {
            
        float furthestPoint = 0;
        for (int i = 0; i < vertices.size(); i++) {
            Vertex currentVertex = vertices.get(i);
            if (currentVertex.getLength() > furthestPoint) {
                furthestPoint = currentVertex.getLength();
            }
            Vector3f position       = currentVertex.getPosition();
            Vector2f textureCoord   = textures.get(currentVertex.getTextureIndex());
            Vector3f normalVector   = normals.get(currentVertex.getNormalIndex());
            
            verticesArray[i * 3]        = position.x;
            verticesArray[i * 3 + 1]    = position.y;
            verticesArray[i * 3 + 2]    = position.z;
            
            texturesArray[i * 2]        = textureCoord.u;
            texturesArray[i * 2 + 1]    = 1 - textureCoord.v;
            
            normalsArray[i * 3]         = normalVector.x;
            normalsArray[i * 3 + 1]     = normalVector.y;
            normalsArray[i * 3 + 2]     = normalVector.z;
        }
        return furthestPoint;
    }

    private static void dealWithAlreadyProcessedVertex(
            Vertex previousVertex,
            int newTextureIndex,
            int newNormalIndex,
            List<Integer> indices,
            List<Vertex> vertices) {
            
        if (previousVertex.hasSameTextureAndNormal(newTextureIndex, newNormalIndex)) {
            indices.add(previousVertex.getIndex());
        } else {
            Vertex anotherVertex = previousVertex.getDuplicateVertex();
            if (anotherVertex != null) {
                dealWithAlreadyProcessedVertex(anotherVertex, newTextureIndex, newNormalIndex, indices, vertices);
            } else {
                Vertex duplicateVertex = new Vertex(vertices.size(), previousVertex.getPosition());
                duplicateVertex.setTextureIndex(newTextureIndex);
                duplicateVertex.setNormalIndex(newNormalIndex);
                previousVertex.setDuplicateVertex(duplicateVertex);
                vertices.add(duplicateVertex);
                indices.add(duplicateVertex.getIndex());
            }
        }
    }

    private static void removeUnusedVertices(List<Vertex> vertices){
        for(Vertex vertex : vertices){
            if(!vertex.isSet()){
                vertex.setTextureIndex(0);
                vertex.setNormalIndex(0);
            }
        }
    }

}