# OpenGL Playground project

Needed this project as an OpenGL playground to test out [LWJGL3](https://www.lwjgl.org/).

## Official Documentation

Basic installation information can be found in this [README](README.md) file. Additional information on LWJGL3 can be found online.

## Change log

Please see [CHANGELOG](CHANGELOG.md) for more information what has changed recently.

## Installation

### LWJGL3 library

* Download latest LWJGL3 library and set it up so it can be reached via project properties
* Add LWJGL3 library to current project via project properties - library
* Add run VM options: _-Dorg.lwjgl.librarypath="/home/avrbanac/Dropbox/Devel/LWJGL3/native"_

### OpenGL

Had some problems with GLSL and the current machine im writhing this on, instead of using
version 400 CORE im stuck with 1.30 for shaders. This can be defined in _shader.vert_ and _shader.frag_ files.

It is important to use a version that enables the use of uniform variables in shader programs.

## Current version

~Application is currently under construction. Target version for something that works is 1.0.0.~

Application is currently in version 1.0.10.

LWJGL library used for this project is in version 3.

## Supported features

* display management in OpenGL
* vertex and fragment shaders
* loading objects and textures
* VAO and VBO management
* transformation, projection and view matrix calculation
* multitextured terrain
* transparency
* implemented entities: objects, player, camera, light, terrain
* diffusal and specular lighting
* fog
* 3rd person view
* mipmapping
* terrain height (with terrain collision detection)
* texture atlases
* GUI rendering
* multiple lighting


## Copyright

YEAH, right....