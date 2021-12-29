package pl.edu.pw.mini.cadcam.pusn.graphics;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;
import pl.edu.pw.mini.cadcam.pusn.model.Mesh;
import pl.edu.pw.mini.cadcam.pusn.model.Renderable;

public class Renderer {
    private final Shader shader;

    public Renderer(GL4 gl) {
        shader = new Shader(gl, "/shaders/default.vert", "/shaders/default.frag");
        gl.glClearColor(0f, 0f, 0f, 1.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
    }

    public void render(GL4 gl, Scene scene, Viewport viewport) {
        gl.glViewport(viewport.getX(), viewport.getY(), viewport.getWidth(), viewport.getHeight());
        scene.getCamera().setAspect((float) viewport.getWidth() / viewport.getHeight());
        scene.getModels().forEach(model -> {
            model.validate(gl);
            model.render(gl, scene.getCamera(), this);
        });
    }

    public void clearColorAndDepth(GL4 gl) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    }

    public void dispose(GL4 gl) {
        shader.dispose(gl);
    }

    public void render(GL4 gl, Camera camera, Renderable model) {
        gl.glLineWidth(1);

        Matrix4f mvp = new Matrix4f(camera.getViewProjectionMatrix());
        mvp.mul(model.getModelMatrix());

        gl.glUseProgram(shader.getProgramID());

        shader.loadMatrix4f(gl, "mvp", mvp);
        shader.loadMatrix4f(gl, "model", model.getModelMatrix());
        shader.loadVector3f(gl, "color", model.getColor());
        shader.loadVector3f(gl, "viewPos", camera.getPosition());

        for (Mesh mesh : model.getMeshes()) {
            gl.glBindVertexArray(mesh.getVao());
            gl.glDrawElements(mesh.getPrimitivesType(), mesh.vertexCount(),
                    GL4.GL_UNSIGNED_INT, 0);
            gl.glBindVertexArray(0);
        }
        gl.glUseProgram(0);
    }
}
