package pl.edu.pw.mini.cadcam.pusn.graphics;

import org.joml.*;

import static org.joml.Math.*;

public class Camera {
    private float aspect;
    private float near;
    private float far;
    private float fov;

    private final Vector3f position;
    private final Vector3f rotation;

    private final Matrix4f viewMatrix;
    private final Matrix4f projectionMatrix;
    private final Matrix4f viewProjectionMatrix;

    public Camera(float aspect, float near, float far, float fov) {
        this.aspect = aspect;
        this.near = near;
        this.far = far;
        this.fov = fov;
        this.position = new Vector3f();
        this.rotation = new Vector3f();
        this.viewMatrix = new Matrix4f();
        this.projectionMatrix = new Matrix4f();
        this.viewProjectionMatrix = new Matrix4f();
        calculateProjectionMatrix();
    }

    private void calculateViewMatrix() {
        Vector3f front = getFront();
        Vector3f up = getUp();
        viewMatrix.identity().lookAt(position, position.add(front, new Vector3f()), up);
        viewProjectionMatrix.set(projectionMatrix)
                .mul(viewMatrix);
    }

    private void calculateProjectionMatrix() {
        projectionMatrix.setPerspective(toRadians(fov), aspect, near, far);
        viewProjectionMatrix.set(projectionMatrix)
                .mul(viewMatrix);
    }

    public float getAspect() {
        return aspect;
    }

    public float getNear() {
        return near;
    }

    public float getFar() {
        return far;
    }

    public float getFov() {
        return fov;
    }

    public Vector3fc getPosition() {
        return position;
    }

    public Vector3fc getRotation() {
        return rotation;
    }

    public Matrix4fc getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4fc getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4fc getViewProjectionMatrix() {
        return viewProjectionMatrix;
    }

    public Vector3f getRight() {
        return getFront().cross(new Vector3f(0, 1, 0)).normalize();
    }

    public Vector3f getUp() {
        return getRight().cross(getFront());
    }

    public Vector3f getFront() {
        return new Vector3f(cos(toRadians(rotation.y)) * cos(toRadians(rotation.x)),
                sin(toRadians(rotation.y)), cos(toRadians(rotation.y)) * sin(toRadians(rotation.x))).normalize();
    }

    public void setAspect(float aspect) {
        this.aspect = aspect;
        calculateProjectionMatrix();
    }

    public void setNear(float near) {
        this.near = near;
        calculateProjectionMatrix();
    }

    public void setFar(float far) {
        this.far = far;
        calculateProjectionMatrix();
    }

    public void setFov(float fov) {
        this.fov = fov;
        calculateProjectionMatrix();
    }

    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
        calculateViewMatrix();
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.set(x, y, z);
        calculateViewMatrix();
    }

    public void move(float dx, float dy, float dz) {
        Vector3f front = getFront();
        Vector3f right = getRight();
        position.add(front.mul(dz));
        position.add(right.mul(dx));
        position.y += dy;
        calculateViewMatrix();
    }

    public void rotate(float dx, float dy, float dz) {
        rotation.x += dx;
        rotation.y += dy;
        rotation.z += dz;
        calculateViewMatrix();
    }
}
