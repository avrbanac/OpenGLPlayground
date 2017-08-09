
package hr.avrbanac.openglplayground.maths;

import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.utils.BufferUtils;
import java.nio.FloatBuffer;

/**
 * Custom matrix math class needed for matrix manipulation.
 * 
 * @author avrbanac
 * @version 1.0.14
 */
public class Matrix4f {
    
    public static final int SIZE = 4 * 4;
    public float[] elements = new float[SIZE];
    
    public Matrix4f() {
        for (int i = 0; i < SIZE; i++) {
            elements[i] = 0.0f;
        }
    }
    
    public static Matrix4f identity() {
        Matrix4f result = new Matrix4f();
        
        result.elements[0 + 0 * 4] = 1.0f;
        result.elements[1 + 1 * 4] = 1.0f;
        result.elements[2 + 2 * 4] = 1.0f;
        result.elements[3 + 3 * 4] = 1.0f;
        
        return result;
    }
    
    public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f result = identity();
        
        result.elements[0 + 0 * 4] = 2.0f / (right - left);
        
        result.elements[1 + 1 * 4] = 2.0f / (top - bottom);
        
        result.elements[2 + 2 * 4] = 2.0f / (near - far);
        
        result.elements[0 + 3 * 4] = (left + right) / (left - right);
        result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
        result.elements[2 + 3 * 4] = (far + near)   / (far - near);
        
        return result;
    }
    
    public static Matrix4f translate(Vector3f vector) {
        Matrix4f result = identity();
        
        result.elements[0 + 3 * 4] = vector.x;
        result.elements[1 + 3 * 4] = vector.y;
        result.elements[2 + 3 * 4] = vector.z;
        
        return result;
    }
    
    public static Matrix4f scale(float scale) {
        return scale(new Vector3f(scale, scale, scale));
    }
    
    public static Matrix4f scale(Vector3f vector) {
        Matrix4f result = identity();
        
        result.elements[0 + 0 * 4] = vector.x;
        result.elements[1 + 1 * 4] = vector.y;
        result.elements[2 + 2 * 4] = vector.z;
        
        return result;
    }
    
    public void rotateZ(float angle) {
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);
        
        this.elements[0 + 0 * 4] =  cos;
        this.elements[1 + 0 * 4] =  sin;
        this.elements[0 + 1 * 4] = -sin;
        this.elements[1 + 1 * 4] =  cos;  
    }
    
    public static Matrix4f rotationZ(float angle) {
        Matrix4f result = identity();
        
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);
        
        result.elements[0 + 0 * 4] =  cos;
        result.elements[1 + 0 * 4] =  sin;
        result.elements[0 + 1 * 4] = -sin;
        result.elements[1 + 1 * 4] =  cos;
        
        return result;
    }
    
    public void rotateY(float angle) {
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);
        
        this.elements[0 + 0 * 4] =  cos;
        this.elements[2 + 0 * 4] = -sin;
        this.elements[0 + 2 * 4] =  sin;
        this.elements[2 + 2 * 4] =  cos;
    }
    
    public static Matrix4f rotationY(float angle) {
        Matrix4f result = identity();
        
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);
        
        result.elements[0 + 0 * 4] =  cos;
        result.elements[2 + 0 * 4] = -sin;
        result.elements[0 + 2 * 4] =  sin;
        result.elements[2 + 2 * 4] =  cos;
        
        return result;
    }
    
    public void rotateX(float angle) {
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);
        
        this.elements[1 + 1 * 4] =  cos;
        this.elements[2 + 1 * 4] =  sin;
        this.elements[1 + 2 * 4] = -sin;
        this.elements[2 + 2 * 4] =  cos;
    }
    
    public static Matrix4f rotationX(float angle) {
        Matrix4f result = identity();
        
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);
        
        result.elements[1 + 1 * 4] =  cos;
        result.elements[2 + 1 * 4] =  sin;
        result.elements[1 + 2 * 4] = -sin;
        result.elements[2 + 2 * 4] =  cos;
        
        return result;
    }
    
    public void rotate(float angle, float x, float y, float z) {
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);
        float omc = (float) 1.0f - cos;
        
        this.elements[0 + 0 * 4] = x * omc + cos;
        this.elements[1 + 0 * 4] = y * x * omc + z * sin;
        this.elements[2 + 0 * 4] = x * z * omc - y * sin;
        
        this.elements[0 + 1 * 4] = x * y * omc - z * sin;
        this.elements[1 + 1 * 4] = y * omc + cos;
        this.elements[2 + 1 * 4] = y * z * omc + x * sin;
        
        this.elements[0 + 2 * 4] = x * z * omc + y * sin;
        this.elements[1 + 2 * 4] = y * z * omc - x * sin;
        this.elements[2 + 2 * 4] = z * omc + cos;
    }
    
    public static Matrix4f rotation(float angle, float x, float y, float z) {
        Matrix4f result = identity();
        
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);
        float omc = (float) 1.0f - cos;
        
        result.elements[0 + 0 * 4] = x * omc + cos;
        result.elements[1 + 0 * 4] = y * x * omc + z * sin;
        result.elements[2 + 0 * 4] = x * z * omc - y * sin;
        
        result.elements[0 + 1 * 4] = x * y * omc - z * sin;
        result.elements[1 + 1 * 4] = y * omc + cos;
        result.elements[2 + 1 * 4] = y * z * omc + x * sin;
        
        result.elements[0 + 2 * 4] = x * z * omc + y * sin;
        result.elements[1 + 2 * 4] = y * z * omc - x * sin;
        result.elements[2 + 2 * 4] = z * omc + cos;
        
        return result;
    }
    
    public Matrix4f multiply(Matrix4f matrix) {
        Matrix4f result = new Matrix4f();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                float sum = 0.0f;
                for (int e = 0; e < 4; e++) {
                    sum += this.elements[x + e * 4] * matrix.elements[e + y * 4];
                }
                result.elements[x + y * 4] = sum;
            }
        }
        
        return result;
    }
    
    public static Matrix4f transformation(Vector2f translation, Vector2f scale) {
        Matrix4f translationMatrix  = translate(new Vector3f(translation.u, translation.v, 0f));
        Matrix4f scaleMatrix        = scale(new Vector3f(scale.u, scale.v, 0f));
        
        return translationMatrix.multiply(scaleMatrix);
    }
    
    public static Matrix4f transformation(Vector3f translation, float rx, float ry, float rz, float scale) {
        Matrix4f translationMatrix  = translate(translation);
        Matrix4f rotationXMatrix    = rotation(rx, 1, 0 ,0);
        Matrix4f rotationYMatrix    = rotation(ry, 0, 1, 0);
        Matrix4f rotationZMatrix    = rotation(rz, 0, 0 ,1);
        Matrix4f scaleMatrix        = scale(scale);
        
        return (((translationMatrix
                .multiply(rotationXMatrix))
                .multiply(rotationYMatrix))
                .multiply(rotationZMatrix))
                .multiply(scaleMatrix);   
    }
    
    public static Vector4f transform(Matrix4f left, Vector4f right, Vector4f dest) {
        if (dest == null) dest = new Vector4f();

        float x = left.elements[0] * right.x + left.elements[4] * right.y + left.elements[8] * right.z + left.elements[12] * right.w;
        float y = left.elements[1]* right.x + left.elements[5] * right.y + left.elements[9] * right.z + left.elements[13] * right.w;
        float z = left.elements[2] * right.x + left.elements[6] * right.y + left.elements[10] * right.z + left.elements[14] * right.w;
        float w = left.elements[3] * right.x + left.elements[7] * right.y + left.elements[11] * right.z + left.elements[15] * right.w;

        dest.x = x;
        dest.y = y;
        dest.z = z;
        dest.w = w;

        return dest;
    }

    
    public static Matrix4f projection() {
        return projection(DISPLAY_WIDTH, DISPLAY_HEIGHT, FOV, NEAR_PLANE, FAR_PLANE);
    }
    
    public static Matrix4f projection(int screenWidth, int screenHeight, float fov) {
        return projection(screenWidth, screenHeight, fov, NEAR_PLANE, FAR_PLANE);
    }
    
    public static Matrix4f projection(int screenWidth, int screenHeight, float fov, float nearPlane, float farPlane) {
        Matrix4f result = new Matrix4f();
        
        // viewport = 1/aspectRatio
        float aspectRatio = (float)screenWidth / (float)screenHeight;
        float focalLength = (float)(1f / Math.tan(Math.toRadians(fov / 2f))); 
        float frustumLength = farPlane - nearPlane;
        
        // xScale
        result.elements[0 + 0 * 4] = focalLength;
        // yScale
        result.elements[1 + 1 * 4] = focalLength * aspectRatio;
        
        result.elements[2 + 2 * 4] = -((farPlane + nearPlane) / frustumLength);
        result.elements[3 + 2 * 4] = -1;
        
        result.elements[2 + 3 * 4] = -((2 * nearPlane * farPlane) / frustumLength);
        result.elements[3 + 3 * 4] = 0;
        
        return result;
    }
    
    public static Matrix4f view(Camera camera) {
        Matrix4f rotationXMatrix = rotation(camera.getPitch(),1,0,0);
        Matrix4f rotationYMatrix = rotation(camera.getYaw(),0,1,0);
        Matrix4f rotationZMatrix = rotation(camera.getRoll(),0,0,1);
        
        // for view Matrix movement everything must be reversed
        Vector3f cameraPosition     = camera.getPosition();
        Vector3f negativeCameraPos  = new Vector3f(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);
        Matrix4f translateMatrix = translate(negativeCameraPos);
        
        return ((rotationXMatrix
                .multiply(rotationZMatrix))
                .multiply(rotationYMatrix))
                .multiply(translateMatrix);
    }
    
    public static Matrix4f inverse(Matrix4f matrix) {
        Matrix4f result = new Matrix4f();
        
        float s0 = matrix.elements[0] * matrix.elements[5]  - matrix.elements[1] * matrix.elements[4];
        float s1 = matrix.elements[0] * matrix.elements[9]  - matrix.elements[1] * matrix.elements[8];
        float s2 = matrix.elements[0] * matrix.elements[13] - matrix.elements[1] * matrix.elements[12];
        float s3 = matrix.elements[4] * matrix.elements[9]  - matrix.elements[5] * matrix.elements[8];
        float s4 = matrix.elements[4] * matrix.elements[13] - matrix.elements[5] * matrix.elements[12];
        float s5 = matrix.elements[8] * matrix.elements[13] - matrix.elements[9] * matrix.elements[12];
        
        float c5 = matrix.elements[10]  * matrix.elements[15]   - matrix.elements[11]   * matrix.elements[14];
        float c4 = matrix.elements[6]   * matrix.elements[15]   - matrix.elements[7]    * matrix.elements[14];
        float c3 = matrix.elements[6]   * matrix.elements[11]   - matrix.elements[7]    * matrix.elements[10];
        float c2 = matrix.elements[2]   * matrix.elements[15]   - matrix.elements[3]    * matrix.elements[14];
        float c1 = matrix.elements[2]   * matrix.elements[11]   - matrix.elements[3]    * matrix.elements[10];
        float c0 = matrix.elements[2]   * matrix.elements[7]    - matrix.elements[3]    * matrix.elements[6];
        
        float invdet = 1.0f / (s0 * c5 - s1 * c4 + s2 * c3 + s3 * c2 - s4 * c1 + s5 * c0);
        
        result.elements[0] = ( matrix.elements[5] * c5 - matrix.elements[9] * c4 + matrix.elements[13] * c3) * invdet;
        result.elements[4] = (-matrix.elements[4] * c5 + matrix.elements[8] * c4 - matrix.elements[12] * c3) * invdet;
        result.elements[8] = ( matrix.elements[7] * s5 - matrix.elements[11] * s4 + matrix.elements[15] * s3) * invdet;
        result.elements[12] = (-matrix.elements[6] * s5 + matrix.elements[10] * s4 - matrix.elements[14] * s3) * invdet;

        result.elements[1] = (-matrix.elements[1] * c5 + matrix.elements[9] * c2 - matrix.elements[13] * c1) * invdet;
        result.elements[5] = ( matrix.elements[0] * c5 - matrix.elements[8] * c2 + matrix.elements[12] * c1) * invdet;
        result.elements[9] = (-matrix.elements[3] * s5 + matrix.elements[11] * s2 - matrix.elements[15] * s1) * invdet;
        result.elements[13] = ( matrix.elements[2] * s5 - matrix.elements[10] * s2 + matrix.elements[14] * s1) * invdet;

        result.elements[2] = ( matrix.elements[1] * c4 - matrix.elements[5] * c2 + matrix.elements[13] * c0) * invdet;
        result.elements[6] = (-matrix.elements[0] * c4 + matrix.elements[4] * c2 - matrix.elements[12] * c0) * invdet;
        result.elements[10] = ( matrix.elements[3] * s4 - matrix.elements[7] * s2 + matrix.elements[15] * s0) * invdet;
        result.elements[14] = (-matrix.elements[2] * s4 + matrix.elements[6] * s2 - matrix.elements[14] * s0) * invdet;

        result.elements[3] = (-matrix.elements[1] * c3 + matrix.elements[5] * c1 - matrix.elements[9] * c0) * invdet;
        result.elements[7] = ( matrix.elements[0] * c3 - matrix.elements[4] * c1 + matrix.elements[8] * c0) * invdet;
        result.elements[11] = (-matrix.elements[3] * s3 + matrix.elements[7] * s1 - matrix.elements[11] * s0) * invdet;
        result.elements[15] = ( matrix.elements[2] * s3 - matrix.elements[6] * s1 + matrix.elements[10] * s0) * invdet;
        
        return result;
    }
    
    public FloatBuffer toFloatBuffer() {
        return BufferUtils.createFloatBuffer(elements);
    }
}
