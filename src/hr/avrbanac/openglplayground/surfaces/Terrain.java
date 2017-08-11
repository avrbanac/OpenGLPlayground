
package hr.avrbanac.openglplayground.surfaces;

import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.models.RawModel;
import hr.avrbanac.openglplayground.loaders.ModelLoader;
import hr.avrbanac.openglplayground.maths.Vector2f;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.textures.TerrainTexture;
import hr.avrbanac.openglplayground.textures.TerrainTexturePack;
import hr.avrbanac.openglplayground.utils.MathUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author avrbanac
 * @version 1.0.8
 */
public class Terrain {
    private final float x;
    private final float z;
    
    private final RawModel model;
    private final TerrainTexturePack texturePack;
    private final TerrainTexture blendMap;
    
    private float[][] heights;
    
    public Terrain(
            int gridX, int gridZ, 
            ModelLoader loader,
            TerrainTexturePack texturePack,
            TerrainTexture blendMap,
            String heightMapFilename) {
        this.texturePack    = texturePack;
        this.blendMap       = blendMap;
        this.x = gridX * TERRAIN_SIZE;
        this.z = gridZ * TERRAIN_SIZE;
        
        this.model = generateTerrain(loader, heightMapFilename);
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public RawModel getModel() {
        return model;
    }

    public TerrainTexturePack getTexturePack() {
        return texturePack;
    }

    public TerrainTexture getBlendMap() {
        return blendMap;
    }
    
    private RawModel generateTerrain(ModelLoader loader, String heightMapFilename) {
        
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(HEIGHT_MAP_FOL + heightMapFilename + HEIGHT_MAP_EXT));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        int vertexCount = (image != null) ? image.getHeight() : 0;
        int count = vertexCount * vertexCount;
        heights = new float[vertexCount][vertexCount];
        
        float[] vertices        = new float[count * 3];
        float[] normals         = new float[count * 3];
        float[] textureCoords   = new float[count*2];
        int[] indices           = new int[6*(vertexCount-1)*(vertexCount-1)];
        
        int vertexPointer = 0;
        for(int i = 0; i < vertexCount; i++){
            for(int j = 0; j < vertexCount; j++){
                vertices[vertexPointer*3]   = (float)j/((float)vertexCount - 1) * TERRAIN_SIZE;
                float height = getHeight(j, i, image);
                heights[j][i] = height;
                vertices[vertexPointer*3+1] = height;
                vertices[vertexPointer*3+2] = (float)i/((float)vertexCount - 1) * TERRAIN_SIZE;
                
                Vector3f normal = calculateNormal(j, i, image);
                normals[vertexPointer*3]    = normal.x;
                normals[vertexPointer*3+1]  = normal.y;
                normals[vertexPointer*3+2]  = normal.z;
                textureCoords[vertexPointer*2]      = (float)j/((float)vertexCount - 1);
                textureCoords[vertexPointer*2+1]    = (float)i/((float)vertexCount - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for(int gz = 0; gz < vertexCount-1; gz++){
            for(int gx = 0; gx < vertexCount-1; gx++){
                int topLeft     = (gz*vertexCount)+gx;
                int topRight    = topLeft + 1;
                int bottomLeft  = ((gz+1)*vertexCount)+gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }
    
    public float getHeightOfTerrain(float worldX, float worldZ) {
        float terrainX = worldX - this.x;
        float terrainZ = worldZ - this.z;
        float gridSquareSize = TERRAIN_SIZE / ((float)heights.length - 1);
        
        int gridX = (int) Math.floor(terrainX / gridSquareSize);
        int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
        
        if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) {
            return 0;
        }
        float xCoord = (terrainX % gridSquareSize)/gridSquareSize;
        float zCoord = (terrainZ % gridSquareSize)/gridSquareSize;
        float answer;
        // now find out in which triangle of the gridSquare point is and then barycentric interpolation :P
        if (xCoord <= (1-zCoord)) {
            answer = MathUtils.barryCentric(
                    new Vector3f(0, heights[gridX][gridZ], 0),
                    new Vector3f(1, heights[gridX + 1][gridZ], 0),
                    new Vector3f(0, heights[gridX][gridZ + 1], 1),
                    new Vector2f(xCoord, zCoord));
	} else {
            answer = MathUtils.barryCentric(
                    new Vector3f(1, heights[gridX + 1][gridZ], 0),
                    new Vector3f(1, heights[gridX + 1][gridZ + 1], 1),
                    new Vector3f(0, heights[gridX][gridZ + 1], 1),
                    new Vector2f(xCoord, zCoord));
	}
        
        return answer;
    }
    
    private float getHeight(int x, int z, BufferedImage image) {
        if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
            // out of height map
            return 0;
        }
        // range: -max, 0
        float height = image.getRGB(x,z);
        // range: -max/2 , max,2
        height += MAX_PIXEL_COLOR / 2f;
        // range: -1,1
        height /= MAX_PIXEL_COLOR / 2f;
        // range -TERRAIN_MAX, TERREAIN_MAX
        height *= TERRAIN_MAX_HEIGHT;
        
        return height;
    }
    
    private Vector3f calculateNormal(int x, int z, BufferedImage image) {
        // calculate height of the neighbouring vertices
        float heightL = getHeight(x - 1, z, image);
        float heightR = getHeight(x + 1, z, image);
        float heightD = getHeight(x, z - 1, image);
        float heightU = getHeight(x, z + 1, image);
        
        Vector3f normal = new Vector3f(heightL - heightR, 2f, heightD - heightU);
        
        return normal.normalise();
    }
}
