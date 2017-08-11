#version 130

in vec4 clipSpace;
in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;

uniform sampler2D dudvMap;
uniform float moveFactorOffset;

const float waveStrength = 0.02;

void main(void){
    vec2 normDeviceSpace = clipSpace.xy / clipSpace.w;
    vec2 screenSpaceCoords = normDeviceSpace / 2.0 + 0.5;

    vec2 refractTexCoords = vec2(screenSpaceCoords.xy);
    vec2 reflectTexCoords = vec2(screenSpaceCoords.x, -screenSpaceCoords.y);

    vec2 distortion1 = (texture(dudvMap, vec2(textureCoords.x + moveFactorOffset, textureCoords.y)).rg * 2.0 - 1.0) * waveStrength;
    vec2 distortion2 = (texture(dudvMap, vec2(-textureCoords.x + moveFactorOffset, textureCoords.y + moveFactorOffset)).rg * 2.0 - 1.0) * waveStrength;
    vec2 totalDistortion = distortion1 + distortion2;

    refractTexCoords += totalDistortion;
    refractTexCoords = clamp(refractTexCoords, 0.001, 0.999);
    
    reflectTexCoords += totalDistortion;
    reflectTexCoords.x = clamp(reflectTexCoords.x, 0.001, 0.999);
    reflectTexCoords.y = clamp(reflectTexCoords.y, -0.999, -0.001);
    

    vec4 reflectColor = texture(reflectionTexture, reflectTexCoords);
    vec4 refractColor = texture(refractionTexture, refractTexCoords);
    out_Color = mix(reflectColor, refractColor, 0.5);
    out_Color = mix(out_Color, vec4(0.0, 0.3, 0.5, 1.0), 0.2);
}