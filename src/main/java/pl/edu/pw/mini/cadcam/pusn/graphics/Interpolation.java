package pl.edu.pw.mini.cadcam.pusn.graphics;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static java.lang.Math.abs;

public class Interpolation {
    private final float startTime;
    private final float endTime;
    private final Vector3f startRotation;
    private final Vector3f endRotation;

    private final Vector3f startPosition;
    private final Vector3f endPosition;

    public Interpolation(float startTime, float endTime, Vector3f startRotation, Vector3f endRotation, Vector3f startPosition, Vector3f endPosition) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startRotation = startRotation;
        this.endRotation = endRotation;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public float getStep(float time) {
        float t = (time - startTime) / (endTime - startTime);
        if(t > 1) t = 1;
        if(t < 0) t = 0;
        return t;
    }

    public float getDuration() {
        return endTime - startTime;
    }

    public float getStartTime() {
        return startTime;
    }

    public Matrix4f interpolateEuler(float time) {
        float t = getStep(time);
        Vector3f rotation = startRotation.lerp(endRotation, t, new Vector3f());
        Vector3f position = startPosition.lerp(endPosition, t, new Vector3f());
        return new Matrix4f().identity()
                .translate(position)
                .rotateXYZ(rotation);
    }

    public Matrix4f interpolateQuaternion(float time) {
        float t = getStep(time);
        Quaternionf startQuaternion = new Quaternionf()
                .rotateXYZ(startRotation.x, startRotation.y, startRotation.z);
        Quaternionf endQuaternion = new Quaternionf()
                .rotateXYZ(endRotation.x, endRotation.y, endRotation.z);
        Quaternionf quaternion = startQuaternion.nlerp(endQuaternion, t, new Quaternionf());
        Vector3f position = startPosition.lerp(endPosition, t, new Vector3f());
        return new Matrix4f().identity()
                .translate(position)
                .rotate(quaternion);
    }

    public Matrix4f interpolateSpherical(float time) {
        float t = getStep(time);
        Quaternionf startQuaternion = new Quaternionf()
                .rotateXYZ(startRotation.x, startRotation.y, startRotation.z);
        Quaternionf endQuaternion = new Quaternionf()
                .rotateXYZ(endRotation.x, endRotation.y, endRotation.z);
        Quaternionf quaternion = startQuaternion.slerp(endQuaternion, t, new Quaternionf());
        Vector3f position = startPosition.lerp(endPosition, t, new Vector3f());
        return new Matrix4f().identity()
                .translate(position)
                .rotate(quaternion);
    }
}
