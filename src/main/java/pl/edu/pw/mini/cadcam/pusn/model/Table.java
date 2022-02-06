package pl.edu.pw.mini.cadcam.pusn.model;

import com.jogamp.opengl.GL4;
import org.joml.*;
import pl.edu.pw.mini.cadcam.pusn.graphics.Camera;
import pl.edu.pw.mini.cadcam.pusn.graphics.Renderer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class Table implements Renderable {
    private final Model j1;
    private final Model j2;
    private final Model j3;
    private final Model j4;
    private final Model a1;
    private final Model a2;
    private final Model a3;
    private final Model a4;
    private final Model x1;
    private final Model y1;
    private final Model z1;
    private final Model x2;
    private final Model y2;
    private final Model z2;
    private final List<Model> models;

    public static class Params {
        public double l1 = 1, l2 = 1, l3 = 1, l4 = 1;
        public double p1 = 0, p2 = 0;
    }

    private final Params params = new Params();

    public Table() throws Exception {
        j1 = ModelLoader.load("/models/", "cylinder.obj");
        j1.setColor(new Vector3f(1, 1, 1));
        j2 = ModelLoader.load("/models/", "cylinder.obj");
        j2.setColor(new Vector3f(1, 0, 1));
        j3 = ModelLoader.load("/models/", "cylinder.obj");
        j3.setColor(new Vector3f(0, 1, 1));
        j4 = ModelLoader.load("/models/", "cylinder.obj");
        j4.setColor(new Vector3f(1, 1, 0));
        a1 = ModelLoader.load("/models/", "cylinder.obj");
        a1.setColor(new Vector3f(1, 1, 1));
        a2 = ModelLoader.load("/models/", "cylinder.obj");
        a2.setColor(new Vector3f(1, 0, 1));
        a3 = ModelLoader.load("/models/", "cylinder.obj");
        a3.setColor(new Vector3f(0, 1, 1));
        a4 = ModelLoader.load("/models/", "cylinder.obj");
        a4.setColor(new Vector3f(1, 1, 0));
        x1 = ModelLoader.load("/models/", "cylinder.obj");
        x1.setColor(new Vector3f(1, 0, 0));
        y1 = ModelLoader.load("/models/", "cylinder.obj");
        y1.setColor(new Vector3f(0, 1, 0));
        z1 = ModelLoader.load("/models/", "cylinder.obj");
        z1.setColor(new Vector3f(0, 0.5f, 1));
        x2 = ModelLoader.load("/models/", "cylinder.obj");
        x2.setColor(new Vector3f(1, 0, 0));
        y2 = ModelLoader.load("/models/", "cylinder.obj");
        y2.setColor(new Vector3f(0, 1, 0));
        z2 = ModelLoader.load("/models/", "cylinder.obj");
        z2.setColor(new Vector3f(0, 0.5f, 1));
        models = List.of(x1, y1, z1, a1, a2, a3, a4, x2, y2, z2/**/);
        setMatrices();
    }

    @Override
    public void render(GL4 gl, Camera camera, Renderer renderer) {
        for (Model model : models) {
            renderer.render(gl, camera, model);
        }
    }

    @Override
    public List<Mesh> getMeshes() {
        return models.stream()
                .map(Model::getMeshes)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Model setModelMatrix(Matrix4f matrix) {
        return null;
    }

    @Override
    public Matrix4fc getModelMatrix() {
        return null;
    }

    @Override
    public void validate(GL4 gl) {
        models.forEach(model -> model.validate(gl));
    }

    @Override
    public void load(GL4 gl) {
        models.forEach(model -> model.load(gl));
    }

    @Override
    public void dispose(GL4 gl) {
        models.forEach(model -> model.dispose(gl));
    }

    @Override
    public Vector3f getColor() {
        return null;
    }

    private void setMatrices() {
        Matrix4d model = new Matrix4d()
                .rotateX(-PI / 2);

        Matrix4d mx1 = new Matrix4d(model)
                .rotateZ(-PI / 2)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        x1.setModelMatrix(new Matrix4f(mx1));

        Matrix4d my1 = new Matrix4d(model)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        y1.setModelMatrix(new Matrix4f(my1));

        Matrix4d mz1 = new Matrix4d(model)
                .rotateX(PI / 2)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        z1.setModelMatrix(new Matrix4f(mz1));

        model.rotateX(PI);
        Matrix4d ma1 = new Matrix4d(model)
                .scale(0.1f, 0.5f * params.l1, 0.1f)
                .translate(0, 1, 0);
        a1.setModelMatrix(new Matrix4f(ma1));

        model.translate(0, params.l1, 0).rotateZ(-PI / 2);
        Matrix4d ma2 = new Matrix4d(model)
                .scale(0.1f, 0.5f * params.l2, 0.1f)
                .translate(0, 1, 0);
        a2.setModelMatrix(new Matrix4f(ma2));

        model.translate(0, params.l2, 0).rotateX(-PI / 4);
        model.rotateY(params.p1);
        Matrix4d ma3 = new Matrix4d(model)
                .scale(0.1f, 0.5f * params.l3, 0.1f)
                .translate(0, 1, 0);
        a3.setModelMatrix(new Matrix4f(ma3));

        model.translate(0, params.l3, 0).rotateX(-PI / 4);
        model.rotateY(params.p2);
        Matrix4d ma4 = new Matrix4d(model)
                .scale(0.1f, 0.5f * params.l4, 0.1f)
                .translate(0, 1, 0);
        a4.setModelMatrix(new Matrix4f(ma4));

        model.translate(0, params.l4, 0);

        Matrix4d mx2 = new Matrix4d(model)
                .rotateX(PI / 2)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        x2.setModelMatrix(new Matrix4f(mx2));

        Matrix4d my2 = new Matrix4d(model)
                .rotateZ(-PI / 2)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        y2.setModelMatrix(new Matrix4f(my2));

        Matrix4d mz2 = new Matrix4d(model)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        z2.setModelMatrix(new Matrix4f(mz2));

    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params.l1 = params.l1;
        this.params.l2 = params.l2;
        this.params.l3 = params.l3;
        this.params.l4 = params.l4;
        this.params.p1 = params.p1;
        this.params.p2 = params.p2;
        setMatrices();
    }
}
