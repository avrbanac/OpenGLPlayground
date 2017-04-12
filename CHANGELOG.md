
# Changelog

All Notable changes to `OpenGL Playground` will be documented in this file.

Updates should follow the [Keep a CHANGELOG](http://keepachangelog.com/) principles.

## [Unreleased]
- Something more advance

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