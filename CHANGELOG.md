
# Changelog

All Notable changes to `OpenGL Playground` will be documented in this file.

Updates should follow the [Keep a CHANGELOG](http://keepachangelog.com/) principles.

## [Unreleased]
- mouse picking problem: if ray has more than one point of intersection with terrain
- Something more advance
- rewrite Matrix4f and Vector3f class (lwjgl src)

## [1.0.18] - 2017-09-05 - Normal mapping

### Added
- new objects and textures
- new normal vertex and fragment shaders
- new packages (to keep code separated): normalMappingObjConverter & normal MappingRenderer

### Fixed
- Globals changed so there are new required global variables
- ModelLoader changed so it can load tangents to VAO
- MasterRenderer changed so it can render normal map entities
- Vector3f added sub method
- Vextor2f added sub and set methods

## [1.0.17] - 2017-08-31 - Fresnel effect, normal map and soft edges

### Added
- normal map texture added
- now depth calculation is added so specular highlights are dampened around edges
- now depth calculation is added so after certain depth point, water is no longer transparent 
- texture unit 4 for depth map
- texture unit 3 for normal map

### Fixed
- waterDuDv map texture fixed
- in main game loop when rendering reflection clip plane needs to be a bit higher in order to fix occasional edge glitch
- since normal map now is used for normal calculation this normal can be used for better Fresnel calculation
- reducing distortion around edges
- WaterRenderer now loads depth map onto texture unit 4 (fbos.getRefractionDepthTexture)
- WaterShader now loads depthMap
- waterFragment now takes depth map as uniform variable
- main game loop must load light to WaterRenderer (made sun separated from rest of lights)
- waterVertex shader now takes also light position
- waterFragment shader now takes also light color uniform
- WaterRenderer now loads normal map onto texture unit 3, and also takes Light as argument for light loading
- Globals changes for normal map
- WaterShader now loads also texture unit 3 onto uniform location for normal map
- distortion lines in waterFragment.glsl are changed (found better solution)
- waterFragment now takes newly calculated toCameraVector; reflective and refractive are not mixed 50% each anymore
- waterVertex and WaterShader now load cameraPosition which is needed for dot product with water norm. vec.

## [1.0.16] - 2017-08-11 - FBO rendering, clip plane calculation, DuDv maps

### Added
- DuDv map texturing with moveFactorOffset changing over time
- gl_clipDistance[0] usage for reflection and refraction rendering
- Frame Buffer Object rendering (to get reflection and refraction textures)
- WaterRenderer class
- water vertex & fragment shader
- WaterShader program class
- WaterTile surfice class

### Fixed
- main loop
- MasterRenderer
- Camera can now invert pitch (needed for reflection calculation)
- TerrainShader and terrainVertex shader now both address clipPlane changes
- StaticShader and ShaderProgram now both address clipPlane changes
- shaderVertex now uses gl_clipDistance[0]
- changed code structure again

## [1.0.15] - 2017-08-09 @avrbanac - mouse picking (ray casting)

### Added
- Vector4f class
- MousePicker class for all the mouse ray calculation

### Fixed
- Matrix4f and Vector3f

### Tested
- binded 1 lamp entity to mousePointer: works (mouse y axis works in reverse)

## [1.0.14] - 2017-08-08 @avrbanac - SKYBOX

### Added
- blendFactor in skybox fragment shader so there can be a night / day skybox cycle 
- skybox renderer
- skybox shaders
- TextureData class
- new load method for cube textures in ModelLoader class

### Fixed
- code structure changes
- Matrix4f class rotation methods
- problems with rotation of the view matrix for skybox shader
- slowly rotating skybox around Y-axis: cloud movement trick
- skyboxFragment shader calculates lower and upper limit horizon lines to make fog more realistic
- master renderer now renders skybox too
- loadToVAO method in MoldeLoader now takes number of dimensions as parameter
- small cleanups in OpenGLPlayground

## [1.0.13] - 2017-05-05 @avrbanac

### Added
- LightStand new entity that holds together light object and light source

### Fixed
- internal organization of files in project together with build.xml to enable correct dist folder building
- instead of using OBJSimpleLoader class, no OBJFileLoader class is used (optimization)

## [1.0.12] - 2017-05-04 @avrbanac - LIGHT ATTENUATION

### Added
- new lamp model (for light source to make sense)

### Fixed
- StaticShader and TerrainShader are now loading up uniform variable attenuation
- fragment shaders now take attenuation vec3 for every light source

## [1.0.11] - 2017-05-04 @avrbanac

### Fixed
- added forgotten reflectivity to both fragment shader and terrain fragment shader

## [1.0.10] - 2017-04-19 @avrbanac - MULTIPLE LIGHTING

### Fixed
- MasterRenderer multiple lights support
- StaticShader and TerrainShader support for multiple lighting sources
- fragment shaders now support array of light sources
- vertex shaders now support array of light sources

## [1.0.9] - 2017-04-15 @avrbanac - GUI

### Added
- GUI vertex and fragment shaders
- GuiShader
- GuiRenderer 
- GuiTexture

## [1.0.8] - 2017-04-15 @avrbanac - TERRAIN HEIGHT & COLLISION DETECTION, TEXTURE ATLASES

### Fixed
- added some new fields to ModelTexture and Entity classes (for texture atlases)
- changed ModelTexture to allow use of texture atlases
- changing from hardcoded terrain hight 0 (in Player class) to calculated one
- Terrain now calculates height

## [1.0.7] - 2017-04-14 @avrbanac - INPUTS AS CALLBACKS & MIPMAPPING

### Added
- mipmapping
- KeyboardHandler
- MouseButtonHandler
- MousePosHandler
- MouseScrollHandler

### Fixed
- camera movement now reflects 3rd person view
- use of the input callbacks allows all entities to be constructed without parent window id
- switched from glfwGetKey function to callback use

## [1.0.6] - 2017-04-12 @avrbanac - MULTITEXTURED TERRAIN

### Added
- Time measurement in DisplayManager (needed for moving player around)
- Player entity with basic physics (defined in Globals)
- TerrainTexture
- TerrainTexturePack

### Fixed
- Camera movement
- moved Vertex class from entities to math package
- res folder split to texture subfolder and model subfolder
- main program
- Terrain
- TerrainShader
- ShaderProgram
- terrain shaders

## [1.0.5] - 2017-04-11 @avrbanac - TRANSPARENCY

### Added
- new OBJFileLoader that supports textured scenes (multitexturing)
- transparency and visibility (fog)

### Fixed
- changes to renderers
- changes to main method
- ModelTexture is fixed for fake lighting tricks
- changes to shaders

## [1.0.4] - 2017-04-10 @avrbanac - TERRAIN

### Added
- new shaders
- Terrain classes for terrain addition together with shaders and shader programs
- MasterRenderer class to optimize rendering of multiple object of the same type

### Fixed
- changes to shaders
- moved public prepare method from EntityRenderer to 
- renamed ModelRenderer to EntityRenderer

## [1.0.3] - 2017-04-07 @avrbanac - SPECULAR LIGHTING

### Added
- matrix inverse function (GLSL 1.30 does not support inverse function for mat4)
- specular lighting

### Fixed
- changes to shaders

## [1.0.2] - 2017-04-06 @avrbanac - DIFFUSAL LIGHTING

### Added
- changes to existing classes and shaders to accommodate lighting
- normal vector calculation for lighting
- Light class represents light source

### Fixed
- changes to shaders
- movement of camera is now faster and enables moving in all directions

## [1.0.1] - 2017-04-06 @avrbanac

### Added
- model and texture resource files (examples)
- new matrix manipulation
- Vector2f math class
- OBJ loader

## [1.0.0] - 2017-04-04 @avrbanac

### Added
- OpenGLPlayground code with main method as a point of entry for application
- render engine with ModelLoader class and ModelRenderer class
- Entity class as model wrapper
- ModelTexture class
- RawModel and TexturedModel classes
- Camera class construct needed for viewMatrix management
- Vector3f and Matrix4f custom classes for matrix math manipulation
- BufferUtils class for buffer memory management
- ShaderProgram abstract class for writing shader programs
- vertex shader and fragment shader written in GLSL
- Globals class to keep all globals encapsulated in one place
- DisplayManager class for OpenGL display management
- documentation
- GIT placeholder

### Deprecated
- Nothing

### Fixed
- vertex and fragment shader paths in Globals class

### Removed
- Nothing

### Tested
- Nothing

### Security
- Nothing