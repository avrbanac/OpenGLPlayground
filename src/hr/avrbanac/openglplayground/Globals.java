
package hr.avrbanac.openglplayground;

/**
 * Single place to hold all graphic globals.
 * 
 * @author avrbanac
 * @version 1.0.1
 */
public class Globals {
    public static final int DISPLAY_WIDTH           = 1280;
    public static final int DISPLAY_HEIGHT          = 720;
    public static final String DISPLAY_TITLE        = "GLFW window";
    
    public static final String VERTEX_FILE              = "src/hr/avrbanac/openglplayground/shaders/shader.vert";
    public static final String FRAGMENT_FILE            = "src/hr/avrbanac/openglplayground/shaders/shader.frag";
    public static final String TERRAIN_VERTEX_FILE      = "src/hr/avrbanac/openglplayground/shaders/terrainShader.vert";
    public static final String TERRAIN_FRAGMENT_FILE    = "src/hr/avrbanac/openglplayground/shaders/terrainShader.frag";
    
    public static final String TEXTURE_TYPE         = "PNG";
    public static final String TEXTURE_FOLDER       = "res/";
    public static final String TEXTURE_EXTENSION    = ".png";
    public static final String MODEL_FOLDER         = "res/";
    public static final String MODEL_EXTENSION      = ".obj";
    
    public static final float FOV                   = 70f;
    public static final float NEAR_PLANE            = 0.1f;
    public static final float FAR_PLANE             = 1000.0f;
    
    public static final float TERRAIN_SIZE          = 800;
    public static final int TERRAIN_VERTEX_COUNT    = 128;
    
    public static final float SKY_RED               = 0.5f;
    public static final float SKY_GREEN             = 0.5f;
    public static final float SKY_BLUE              = 0.5f;
}

