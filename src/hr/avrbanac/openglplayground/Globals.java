
package hr.avrbanac.openglplayground;

/**
 * Single place to hold all graphic globals.
 * 
 * @author avrbanac
 * @version 1.0.16
 */
public class Globals {
    public static final int DISPLAY_WIDTH           = 1280;
    public static final int DISPLAY_HEIGHT          = 720;
    public static final String DISPLAY_TITLE        = "GLFW window";
    
    public static final String VERTEX_FILE              = "res/shaders/shaderVertex.glsl";
    public static final String FRAGMENT_FILE            = "res/shaders/shaderFragment.glsl";
    public static final String TERRAIN_VERTEX_FILE      = "res/shaders/terrainVertex.glsl";
    public static final String TERRAIN_FRAGMENT_FILE    = "res/shaders/terrainFragment.glsl";
    public static final String GUI_VERTEX_FILE          = "res/shaders/guiVertex.glsl";
    public static final String GUI_FRAGMENT_FILE        = "res/shaders/guiFragment.glsl";
    public static final String SKYBOX_VERTEX_FILE       = "res/shaders/skyboxVertex.glsl";
    public static final String SKYBOX_FRAGMENT_FILE     = "res/shaders/skyboxFragment.glsl";
    public static final String WATER_VERTEX_FILE        = "res/shaders/waterVertex.glsl";
    public static final String WATER_FRAGMENT_FILE      = "res/shaders/waterFragment.glsl";
    
    public static final String TEXTURE_TYPE         = "PNG";
    public static final String TEXTURE_FOLDER       = "res/textures/";
    public static final String TEXTURE_EXTENSION    = ".png";
    public static final String MODEL_FOLDER         = "res/models/";
    public static final String MODEL_EXTENSION      = ".obj";
    public static final String HEIGHT_MAP_EXT       = ".png";
    public static final String HEIGHT_MAP_FOL       = "res/textures/";
    public static final String TEXTURE_CUBE_FILE    = "res/textures/skybox/";
    public static final String TEXTURE_CUBE_EXT     = ".png";
    public static final String DUDV_MAP             = "waterDuDv";
    
    public static final Boolean FIX_SKYBOX_SEAMS    = false;
    public static final Boolean MOUSE_Y_INVERT      = false;
    
    public static final int MOUSE_PICKER_RECURSION_CAP  = 200;
    public static final float MOUSE_RAY_RANGE           = 600;  
    
    public static final float FOV                   = 70f;
    public static final float NEAR_PLANE            = 0.1f;
    public static final float FAR_PLANE             = 1000.0f;
    
    public static final float MAX_PIXEL_COLOR       = 256 * 256 * 256;
    
    public static final float WATER_TILE_SIZE       = 60;
    public static final float TERRAIN_SIZE          = 800;
    public static final float TERRAIN_MAX_HEIGHT    = 40;
    public static final float SKYBOX_SIZE           = 500;
    public static final float SKYBOX_ROT_SPEED      = 1f;
    
    public static final int WATER_REFLECTION_WIDTH  = 320;
    public static final int WATER_REFLECTION_HEIGHT = 180;
    public static final int WATER_REFRACTION_WIDTH  = 1280;
    public static final int WATER_REFRACTION_HEIGHT = 720;
    public static final float WATER_WAVE_SPEED      = 0.03f;
    
    public static final float SKY_RED               = 0.45f;
    public static final float SKY_GREEN             = 0.53f;
    public static final float SKY_BLUE              = 0.59f;
    
    public static final double TO_MILLISECONDS      = 1000;
    
    public static final float RUN_SPEED             = 20;
    public static final float TURN_SPEED            = 160;
    public static final float GRAVITY               = -50;
    public static final float JUMP_POWER            = 30;
    
    public static final float MOUSE_SENS_SCROLL     = 1f;
    public static final float MOUSE_SENS_MOVE_X     = 0.3f;
    public static final float MOUSE_SENS_MOVE_Y     = 0.1f;
    
    public static final float CAMERA_MIN_PITCH      = 5f;
    public static final float CAMERA_MAX_PITCH      = 90f;
    public static final float CAMERA_MIN_DIST       = 25f;
    public static final float CAMERA_MAX_DIST       = 500f;
    
    public static final float MIPMAPPING_FACTOR     = -0.4f;
    
    public static final int MAX_LIGHTS_NUMBER       = 4;
    
    public static final float LIGHT_HEIGHT_OFFSET   = 13;
    public static final float L_ATTENUATION_1       = 1;
    public static final float L_ATTENUATION_2       = 0.01f;
    public static final float L_ATTENUATION_3       = 0.002f;
}

