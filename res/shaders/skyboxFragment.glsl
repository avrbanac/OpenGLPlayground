#version 130

in vec3 textureCoords;
out vec4 out_Color;

uniform samplerCube cubeMap0;
uniform samplerCube cubeMap1;
uniform vec3 fogColor;
uniform float blendFactor;

const float lowerLimit = 0.0;
const float upperLimit = 30.0;

void main(void){
    vec4 texture0 = texture(cubeMap0, textureCoords);
    vec4 texture1 = texture(cubeMap1, textureCoords);
    vec4 finalColor = mix(texture0, texture1, blendFactor);

    float factor = (textureCoords.y - lowerLimit) / (upperLimit - lowerLimit);
    factor = clamp(factor, 0.0, 1.0);

    out_Color = mix(vec4(fogColor, 1.0), finalColor, factor);
}
