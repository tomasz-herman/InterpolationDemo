package pl.edu.pw.mini.cadcam.pusn.model;

import com.jogamp.opengl.GL4;
import org.joml.*;
import pl.edu.pw.mini.cadcam.pusn.graphics.Camera;
import pl.edu.pw.mini.cadcam.pusn.graphics.Renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Model implements Renderable {
    protected List<Mesh> meshes = new ArrayList<>();

    private final Matrix4f modelMatrix;
    protected final AtomicBoolean loaded = new AtomicBoolean();
    private final Vector3f color = new Vector3f(1);

    public Model(Collection<Mesh> meshes) {
        this.meshes.addAll(meshes);
        modelMatrix = new Matrix4f().identity();
    }

    public void render(GL4 gl, Camera camera, Renderer renderer) {
        if(loaded.get()) {
            renderer.render(gl, camera, this);
        }
    }

    public List<Mesh> getMeshes() {
        return meshes;
    }

    public Model setModelMatrix(Matrix4f matrix) {
        modelMatrix.set(matrix);
        return this;
    }

    public Matrix4fc getModelMatrix() {
        return modelMatrix;
    }

    public void validate(GL4 gl) {
        if(!loaded.getAndSet(true)) {
            load(gl);
        }
    }

    public void load(GL4 gl) {
        meshes.forEach(mesh -> mesh.load(gl));
    }

    public void dispose(GL4 gl) {
        meshes.forEach(mesh -> mesh.dispose(gl));
        loaded.set(false);
    }

    @Override
    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color.set(color);
    }
}
