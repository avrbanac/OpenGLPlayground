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

Had some problems with OpenGL capabilities with current machine im writhing this on, instead of using
version 400 CORE im stuck with 1.30 for shaders. This can be defined in _shader.vert_ and _shader.frag_ files.

It is important to use a version that enables the use of uniform variables in shader programs.

## Current version

~Application is currently under construction. Target version for something that works is 1.0.0.~

Application is currently in version 1.0.1.

LWJGL library used for this project is in version 3.

## Copyright

YEAH, right....