
package hr.avrbanac.openglplayground.terrains;

import static hr.avrbanac.openglplayground.Globals.TERRAIN_SIZE;
import static hr.avrbanac.openglplayground.Globals.TERRAIN_VERTEX_COUNT;
import hr.avrbanac.openglplayground.models.RawModel;
import hr.avrbanac.openglplayground.loaders.ModelLoader;
import hr.avrbanac.openglplayground.textures.ModelTexture;
import hr.avrbanac.openglplayground.textures.TerrainTexture;
import hr.avrbanac.openglplayground.textures.TerrainTexturePack;

/**
 *
 * @author avrbanac
 * @version 1.0.6
 */
public class Terrain {
    private float x;
    private float z;
    
    private RawModel model;
    private TerrainTexturePack texturePack;
    private TerrainTexture blendMap;
    
    public Terrain(int gridX, int gridZ, ModelLoader loader, TerrainTexturePack texturePack, TerrainTexture blendMap) {
        this.texturePack    = texturePack;
        this.blendMap       = blendMap;
        this.x = gridX * TERRAIN_SIZE;
        this.z = gridZ * TERRAIN_SIZE;
        
        this.model = generateTerrain(loader);
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
    
    private RawModel generateTerrain(ModelLoader loader){
        int count = TERRAIN_VERTEX_COUNT * TERRAIN_VERTEX_COUNT;
        
        float[] vertices        = new float[count * 3];
        float[] normals         = new float[count * 3];
        float[] textureCoords   = new float[count*2];
        int[] indices           = new int[6*(TERRAIN_VERTEX_COUNT-1)*(TERRAIN_VERTEX_COUNT-1)];
        
        int vertexPointer = 0;
        for(int i = 0; i < TERRAIN_VERTEX_COUNT; i++){
            for(int j = 0; j < TERRAIN_VERTEX_COUNT; j++){
                vertices[vertexPointer*3]   = (float)j/((float)TERRAIN_VERTEX_COUNT - 1) * TERRAIN_SIZE;
                vertices[vertexPointer*3+1] = 0;
                vertices[vertexPointer*3+2] = (float)i/((float)TERRAIN_VERTEX_COUNT - 1) * TERRAIN_SIZE;
                normals[vertexPointer*3]    = 0;
                normals[vertexPointer*3+1]  = 1;
                normals[vertexPointer*3+2]  = 0;
                textureCoords[vertexPointer*2]      = (float)j/((float)TERRAIN_VERTEX_COUNT - 1);
                textureCoords[vertexPointer*2+1]    = (float)i/((float)TERRAIN_VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for(int gz = 0; gz < TERRAIN_VERTEX_COUNT-1; gz++){
            for(int gx = 0; gx < TERRAIN_VERTEX_COUNT-1; gx++){
                int topLeft     = (gz*TERRAIN_VERTEX_COUNT)+gx;
                int topRight    = topLeft + 1;
                int bottomLeft  = ((gz+1)*TERRAIN_VERTEX_COUNT)+gx;
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
}
