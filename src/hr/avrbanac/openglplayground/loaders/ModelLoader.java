
package hr.avrbanac.openglplayground.loaders;

import hr.avrbanac.openglplayground.models.RawModel;
import static hr.avrbanac.openglplayground.utils.BufferUtils.*;
import static hr.avrbanac.openglplayground.Globals.*;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Loads a model into memory by storing data into VAO.
 * 
 * @author avrbanac
 * @version 1.0.2
 */
public class ModelLoader {
    
    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> texs = new ArrayList<>();
    
    public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        // in attribute 0 of VAO is VBO containing positions
        storeDataInAttributeList(0, 3, positions);
        // in attribute 1 of VAO is VBO containing texture coordinates
        storeDataInAttributeList(1, 2, textureCoords);
        // in attribute 2 of VAO store normals (for lighting)
        storeDataInAttributeList(2, 3, normals);
        
        unbindVAO();
        
        return new RawModel(vaoID, indices.length);
    }
    
    private int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        
        return vaoID;
    }
    
    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        
        // store data in VBO, with static draw - we don't edit data once it is stored
        FloatBuffer fBuffer = createFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, fBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
        
        // unbind VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
    
    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }
    
    private void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        
        // store data in VBO, with static draw - we don't edit data once it is stored; now element array buffer: indices
        IntBuffer iBuffer = createIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, iBuffer, GL15.GL_STATIC_DRAW);
    }
    
    public int loadTexture(String fileName) {
        int[] pixels = null;
        int width = 0;
        int height = 0;
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(TEXTURE_FOLDER + fileName + TEXTURE_EXTENSION));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int [width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int[] data = new int [width * height];
        for (int i = 0; i < width * height; i++) {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);
            // OpenGL wants it in this order
            data[i] = a << 24 | b << 16 | g << 8 | r;
        }
        
        int textureID = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11. GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA,GL11. GL_UNSIGNED_BYTE, createIntBuffer(data));
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        
        texs.add(textureID);
        return textureID;
    }
    
    public void cleanUp() {
        // vaos, vbos, texs are just lists to keep track of all VAOs, VBOs, and textures
        vaos.stream().forEach(GL30::glDeleteVertexArrays);
        vbos.stream().forEach(GL15::glDeleteBuffers);
        texs.stream().forEach(GL11::glDeleteTextures);
    }
    
}
