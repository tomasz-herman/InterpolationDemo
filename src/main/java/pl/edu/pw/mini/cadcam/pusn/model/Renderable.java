package pl.edu.pw.mini.cadcam.pusn.model;

import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import pl.edu.pw.mini.cadcam.pusn.graphics.Camera;
import pl.edu.pw.mini.cadcam.pusn.graphics.Renderer;

import java.util.List;

public interface Renderable {
    void render(GL4 gl, Camera camera, Renderer renderer);

    List<Mesh> getMeshes();

    Model setModelMatrix(Matrix4f matrix);

    Matrix4fc getModelMatrix();

    void validate(GL4 gl);

    void load(GL4 gl);

    void dispose(GL4 gl);

    Vector3f getColor();
}
