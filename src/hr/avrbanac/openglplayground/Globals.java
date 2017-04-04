
package hr.avrbanac.openglplayground;

/**
 * Single place to hold all graphic globals.
 * 
 * @author avrbanac
 * @version 1.0.0
 */
public class Globals {
    public static final int DISPLAY_WIDTH           = 1280;
    public static final int DISPLAY_HEIGHT          = 720;
    public static final String DISPLAY_TITLE        = "GLFW window";
    
    public static final String VERTEX_FILE          = "src/hr/avrbanac/opengltutorial/shaders/shader.vert";
    public static final String FRAGMENT_FILE        = "src/hr/avrbanac/opengltutorial/shaders/shader.frag";
    
    public static final String TEXTURE_TYPE         = "PNG";
    public static final String TEXTURE_FOLDER       = "res/";
    public static final String TEXTURE_EXTENSION    = ".png";
    
    public static final float FOV                   = 70;
    public static final float NEAR_PLANE            = 0.1f;
    public static final float FAR_PLANE             = 1000.0f;
}

