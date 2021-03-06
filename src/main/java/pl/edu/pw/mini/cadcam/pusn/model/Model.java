package pl.edu.pw.mini.cadcam.pusn.model;

import com.jogamp.opengl.GL4;
import org.joml.*;
import pl.edu.pw.mini.cadcam.pusn.graphics.Camera;
import pl.edu.pw.mini.cadcam.pusn.graphics.Renderer;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Model {
    protected List<Mesh> meshes = new ArrayList<>();

    private final Matrix4f modelMatrix;
    protected final AtomicBoolean loaded = new AtomicBoolean();

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

    protected void load(GL4 gl) {
        meshes.forEach(mesh -> mesh.load(gl));
    }

    public void dispose(GL4 gl) {
        meshes.forEach(mesh -> mesh.dispose(gl));
        loaded.set(false);
    }
}
