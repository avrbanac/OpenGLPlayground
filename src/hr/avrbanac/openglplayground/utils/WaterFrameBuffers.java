
package hr.avrbanac.openglplayground.utils;

import static hr.avrbanac.openglplayground.Globals.*;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
//import org.lwjgl.opengl.GL32;

/**
 *
 * @author avrbanac
 * @version 1.0.16
 */
public class WaterFrameBuffers {
    private int reflectionFrameBuffer;
    private int reflectionTexture;
    private int reflectionDepthBuffer;
     
    private int refractionFrameBuffer;
    private int refractionTexture;
    private int refractionDepthTexture;
 
    //call this when loading the game
    public WaterFrameBuffers() {
        initialiseReflectionFrameBuffer();
        initialiseRefractionFrameBuffer();
    }
 
    public void cleanUp() {//call when closing the game
        GL30.glDeleteFramebuffers(reflectionFrameBuffer);
        GL11.glDeleteTextures(reflectionTexture);
        GL30.glDeleteRenderbuffers(reflectionDepthBuffer);
        GL30.glDeleteFramebuffers(refractionFrameBuffer);
        GL11.glDeleteTextures(refractionTexture);
        GL11.glDeleteTextures(refractionDepthTexture);
    }
 
    //call before rendering to this FBO
    public void bindReflectionFrameBuffer() {
        bindFrameBuffer(reflectionFrameBuffer, WATER_REFLECTION_WIDTH, WATER_REFLECTION_HEIGHT);
    }
    
    //call before rendering to this FBO
    public void bindRefractionFrameBuffer() {
        bindFrameBuffer(refractionFrameBuffer, WATER_REFRACTION_WIDTH, WATER_REFRACTION_HEIGHT);
    }
    
    //call to switch to default frame buffer
    public void unbindCurrentFrameBuffer() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        GL11.glViewport(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);
    }
 
    public int getReflectionTexture() {
        return reflectionTexture;
    }
     
    public int getRefractionTexture() {
        return refractionTexture;
    }
     
    public int getRefractionDepthTexture(){
        return refractionDepthTexture;
    }
 
    private void initialiseReflectionFrameBuffer() {
        reflectionFrameBuffer = createFrameBuffer();
        reflectionTexture = createTextureAttachment(WATER_REFLECTION_WIDTH, WATER_REFLECTION_HEIGHT);
        reflectionDepthBuffer = createDepthBufferAttachment(WATER_REFLECTION_WIDTH, WATER_REFLECTION_HEIGHT);
        unbindCurrentFrameBuffer();
    }
     
    private void initialiseRefractionFrameBuffer() {
        refractionFrameBuffer = createFrameBuffer();
        refractionTexture = createTextureAttachment(WATER_REFRACTION_WIDTH, WATER_REFRACTION_HEIGHT);
        refractionDepthTexture = createDepthTextureAttachment(WATER_REFRACTION_WIDTH, WATER_REFRACTION_HEIGHT);
        unbindCurrentFrameBuffer();
    }
     
    private void bindFrameBuffer(int frameBuffer, int width, int height){
        //To make sure the texture isn't bound
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        GL11.glViewport(0, 0, width, height);
    }
 
    private int createFrameBuffer() {
        int frameBuffer = GL30.glGenFramebuffers();
        //generate name for frame buffer
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBuffer);
        //create the framebuffer
        GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
        //indicate that we will always render to color attachment 0
        return frameBuffer;
    }
 
    private int createTextureAttachment( int width, int height) {
        int texture = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height,
                0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        // OpenGL 3.0 does not support this:
        // GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, texture, 0);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, texture, 0);
        
        return texture;
    }
     
    private int createDepthTextureAttachment(int width, int height){
        int texture = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT32, width, height,
                0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        // OpenGL 3.0 does not support this:
        // GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, texture, 0);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, texture, 0);
        
        return texture;
    }
 
    private int createDepthBufferAttachment(int width, int height) {
        int depthBuffer = GL30.glGenRenderbuffers();
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, width, height);
        GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, depthBuffer);
        
        return depthBuffer;
    }
}
