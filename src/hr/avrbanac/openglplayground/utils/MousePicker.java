
package hr.avrbanac.openglplayground.utils;

import static hr.avrbanac.openglplayground.Globals.*;
import hr.avrbanac.openglplayground.entities.Camera;
import hr.avrbanac.openglplayground.inputs.MousePosHandler;
import hr.avrbanac.openglplayground.maths.Matrix4f;
import hr.avrbanac.openglplayground.maths.Vector2f;
import hr.avrbanac.openglplayground.maths.Vector3f;
import hr.avrbanac.openglplayground.maths.Vector4f;
import hr.avrbanac.openglplayground.terrains.Terrain;

/**
 *
 * @author avrbanac
 * @version 1.0.15
 */
public class MousePicker {
    private Vector3f currentRay;
    
    private final Matrix4f projectionMatrix;
    private final Camera camera;
    private Matrix4f viewMatrix;
    
    // or make it array if there are more tiles
    private final Terrain terrain;
    private Vector3f currentTerrainPoint;
    
    // also make change from Terrain to Terrain[] if needed
    public MousePicker(Camera camera, Matrix4f projectionMatrix, Terrain terrain) {
        this.camera = camera;
        this.projectionMatrix = projectionMatrix;
        this.viewMatrix = Matrix4f.view(camera);
        this.terrain = terrain;
    }
    
    public Vector3f getCurrentTerrainPoint() {
        return currentTerrainPoint;
    }
    
    public Vector3f getCurrentRay() {
        return currentRay;
    }
    
    public void update() {
        viewMatrix = Matrix4f.view(camera);
        currentRay = calculateMouseRay();
        
        if (intersectionInRange(0, MOUSE_RAY_RANGE, currentRay)) {
            currentTerrainPoint = binarySearch(0, 0, MOUSE_RAY_RANGE, currentRay);
        } else {
            currentTerrainPoint = null;
        }
    }
    
    private Vector3f calculateMouseRay() {
        float mouseX = MousePosHandler.getX();
        float mouseY = MousePosHandler.getY();
        Vector2f normalizedCoords = getNormalizedDeviceCoords(mouseX, mouseY);
        Vector4f clipCoords = new Vector4f(normalizedCoords.u, normalizedCoords.v, -1f, 1f);
        Vector4f eyeCoords = toEyeCoords(clipCoords);
        return toWorldCoords(eyeCoords);
    }
    
    private Vector3f toWorldCoords(Vector4f eyeCoords) {
        Matrix4f invertedView = Matrix4f.inverse(viewMatrix);
        Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
        Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
        return mouseRay.normalise();
    }
    
    private Vector4f toEyeCoords(Vector4f clipCoords) {
        Matrix4f invertedProjection = Matrix4f.inverse(projectionMatrix);
        Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
        return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
    }
    
    private Vector2f getNormalizedDeviceCoords(float mouseX, float mouseY) {
        float x = (2f*mouseX) / DISPLAY_WIDTH - 1f;
        float y = (2f*mouseY) / DISPLAY_HEIGHT - 1f;
        return new Vector2f(x, y);
    }
    
    private Vector3f getPointOnRay(Vector3f ray, float distance) {
        Vector3f camPos = camera.getPosition();
        Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
        Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
        return Vector3f.add(start, scaledRay, null);
    }
    
    // this method will make good calculation as long as ray does not have more then one point of intersection with terrain
    private Vector3f binarySearch(int count, float start, float finish, Vector3f ray) {
        float half = start + ((finish - start) / 2f);
        if (count >= MOUSE_PICKER_RECURSION_CAP) {
            Vector3f endPoint = getPointOnRay(ray, half);
            Terrain currentTerrain = getTerrain(endPoint.x, endPoint.z);
            return (currentTerrain != null) ? endPoint : null;    
        }
        return (intersectionInRange(start, half, ray))
                ? binarySearch(count + 1, start, half, ray)
                : binarySearch(count + 1, half, finish, ray);
   
    }
    
    private boolean intersectionInRange(float start, float finish, Vector3f ray) {
        Vector3f startPoint = getPointOnRay(ray, start);
        Vector3f endPoint = getPointOnRay(ray, finish);
        
        return (!isUnderGround(startPoint) && isUnderGround(endPoint));
    }
    
    private boolean isUnderGround(Vector3f testPoint) {
        Terrain currentTerrain = getTerrain(testPoint.x, testPoint.z);
        float height = 0;
        if (currentTerrain != null) height = currentTerrain.getHeightOfTerrain(testPoint.x, testPoint.z);
        
        return (testPoint.y < height);
    }
    
    private Terrain getTerrain(float worldX, float worldZ) {
        // TODO test if more then one terrain in Terrain[]
        // something like:
        // int x = worldX / Terrain.SIZE;
        // int y = worldY / Terrain.SIZE;
        // return terrains[x][y]
        return terrain;
    }
}
