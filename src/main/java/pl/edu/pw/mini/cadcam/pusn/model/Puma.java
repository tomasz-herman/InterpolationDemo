package pl.edu.pw.mini.cadcam.pusn.model;

import com.jogamp.opengl.GL4;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import pl.edu.pw.mini.cadcam.pusn.graphics.Camera;
import pl.edu.pw.mini.cadcam.pusn.graphics.Renderer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.jogamp.opengl.math.FloatUtil.*;

public class Puma implements Renderable {
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

    private float l1, l2, l3, l4;
    private float p1, p2, p3, p4, p5;

    public Puma() throws Exception {
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
        models = List.of(j1, a1, j2, a2, j3, a3, j4, a4, x1, y1, z1, x2, y2, z2/**/);
        l1 = l2 = l3 = l4 = 3;
        p1 = p2 = p3 = p4 = p5 = -PI / 6;
        Parameters params = inverseKinematics(0, 0, 0, 0, 0, 0);
        System.out.println(params);
        set(params);
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
        Matrix4f model = new Matrix4f()
                .rotateX(-PI / 2);

        Matrix4f mx1 = new Matrix4f(model)
                .rotateZ(-PI / 2)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        x1.setModelMatrix(mx1);

        Matrix4f my1 = new Matrix4f(model)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        y1.setModelMatrix(my1);

        Matrix4f mz1 = new Matrix4f(model)
                .rotateX(PI / 2)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        z1.setModelMatrix(mz1);

        model.rotateZ(p1);
        Matrix4f mj1 = new Matrix4f(model)
                .rotateX(PI / 2)
                .scale(0.2f, 0.2f, 0.2f)
                .translate(0, 1, 0);
        j1.setModelMatrix(mj1);
        Matrix4f ma1 = new Matrix4f(model)
                .rotateX(PI / 2)
                .scale(0.1f, 0.5f * l1, 0.1f)
                .translate(0, 1, 0);
        a1.setModelMatrix(ma1);


        model.translate(0, 0, l1).rotateY(p2);

        Matrix4f mj2 = new Matrix4f(model)
                .rotateZ(-PI / 2)
                .scale(0.2f, 0.2f, 0.2f)
                .rotateZ(PI / 2);
        j2.setModelMatrix(mj2);
        Matrix4f ma2 = new Matrix4f(model)
                .rotateZ(-PI / 2)
                .scale(0.1f, 0.5f * l2, 0.1f)
                .translate(0, 1, 0);
        a2.setModelMatrix(ma2);

        model.translate(l2, 0, 0).rotateY(p3);

        Matrix4f mj3 = new Matrix4f(model)
                .rotateX(-PI / 2)
                .scale(0.2f, 0.2f, 0.2f)
                .rotateX(PI / 2);
        j3.setModelMatrix(mj3);
        Matrix4f ma3 = new Matrix4f(model)
                .rotateX(-PI / 2)
                .scale(0.1f, 0.5f * l3, 0.1f)
                .translate(0, 1, 0);
        a3.setModelMatrix(ma3);

        model.translate(0, 0, -l3).rotateZ(p4);

        Matrix4f mj4 = new Matrix4f(model)
                .rotateZ(-PI / 2)
                .scale(0.2f, 0.2f, 0.2f);
        j4.setModelMatrix(mj4);



        Matrix4f ma4 = new Matrix4f(model)
                .rotateZ(-PI / 2)
                .scale(0.1f, 0.5f * l4, 0.1f)
                .translate(0, 1, 0);
        a4.setModelMatrix(ma4);

        model.translate(l4, 0, 0).rotateX(p5);

        Matrix4f mx2 = new Matrix4f(model)
                .rotateZ(-PI / 2)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        x2.setModelMatrix(mx2);

        Matrix4f my2 = new Matrix4f(model)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        y2.setModelMatrix(my2);

        Matrix4f mz2 = new Matrix4f(model)
                .rotateX(PI / 2)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        z2.setModelMatrix(mz2);
    }

    public float getL1() {
        return l1;
    }

    public void setL1(float l1) {
        this.l1 = l1;
    }

    public float getL2() {
        return l2;
    }

    public void setL2(float l2) {
        this.l2 = l2;
    }

    public float getL3() {
        return l3;
    }

    public void setL3(float l3) {
        this.l3 = l3;
    }

    public float getL4() {
        return l4;
    }

    public void setL4(float l4) {
        this.l4 = l4;
    }

    public float getP1() {
        return p1;
    }

    public void setP1(float p1) {
        this.p1 = p1;
    }

    public float getP2() {
        return p2;
    }

    public void setP2(float p2) {
        this.p2 = p2;
    }

    public float getP3() {
        return p3;
    }

    public void setP3(float p3) {
        this.p3 = p3;
    }

    public float getP4() {
        return p4;
    }

    public void setP4(float p4) {
        this.p4 = p4;
    }

    public float getP5() {
        return p5;
    }

    public void setP5(float p5) {
        this.p5 = p5;
    }

    public Parameters inverseKinematics(float tx, float ty, float tz, float rx, float ry, float rz) {
        Parameters r = new Parameters();
        Matrix3f rot = new Matrix3f().rotateXYZ(rx, ry, rz);
        Vector3f x = rot.getColumn(0, new Vector3f());
        Vector3f y = rot.getColumn(1, new Vector3f());
        Vector3f z = rot.getColumn(2, new Vector3f());
        float x1 = x.x, x2 = x.y, x3 = x.z;
        float y1 = y.x, y2 = y.y, y3 = y.z;
        float z1 = z.x, z2 = z.y, z3 = z.z;
        float p1 = tx, p2 = ty, p3 = tz;
        r.p1 = atan2(p2 - l4 * x2, p1 - l4 * x1);
        float s1 = sin(r.p1), c1 = cos(r.p1);
        r.p4 = asin(c1 * x2 - s1 * x1);
        float s4 = sin(r.p4), c4 = cos(r.p4);
        r.p5 = atan2(s1 * z1 - c1 * z2, c1 * y2 - s1 * y1);
        r.p2 = atan2(-(c1 * c4 * (p3 - l4 * x3 - l1) + l3 * (x1 + s1 * s4)) , (c4 * (p1 - l4 * x1) - c1 * l3 * x3));
        float s2 = sin(r.p2), c2 = cos(r.p2);
        r.l2 = (c4 * (p1 - l4 * x1) - c1 * l3 * x3) / (c1 * c2 * c4);
        r.p3 = atan2(-x3, (x.x + s1 * s4) / c1) - r.p2;

        System.out.println(r);
        System.out.println(new Matrix4f()
                .rotateZ(r.p1).translate(0, 0, l1)
                .rotateY(r.p2).translate(r.l2, 0, 0)
                .rotateY(r.p3).translate(0, 0, -l3)
                .rotateZ(r.p4).translate(l4, 0, 0)
                .rotateX(r.p5));

        System.out.println(new Vector3f(
                cos(r.p1) * (cos(r.p2) * (r.l2 - sin(r.p3) * l3 + cos(r.p3) * cos(r.p4) * l4) - sin(r.p2) * (cos(r.p3) * l3 + sin(r.p3) * cos(r.p4) * l4)) - sin(r.p1) * sin(r.p4) * l4,
                sin(r.p1) * (cos(r.p2) * (r.l2 - sin(r.p3) * l3 + cos(r.p3) * cos(r.p4) * l4) - sin(r.p2) * (cos(r.p3) * l3 + sin(r.p3) * cos(r.p4) * l4)) - cos(r.p1) * sin(r.p4) * l4,
                -sin(r.p2) * (r.l2 - sin(r.p3) * l3 + cos(r.p3) * cos(r.p4) * l4) - cos(r.p2) * (cos(r.p3) * l3 + sin(r.p3) * cos(r.p4) * l4) + l1
        ));

        return r;
    }

    public void set(Parameters p) {
        setL2(p.l2);
        setP1(p.p1);
        setP2(p.p2);
        setP3(p.p3);
        setP4(p.p4);
        setP5(p.p5);
        setMatrices();
    }

    public static class Parameters {
        public float l2;
        public float p1, p2, p3, p4, p5;

        @Override
        public String toString() {
            return "Parameters{" +
                   "l2=" + l2 +
                   ", p1=" + p1 +
                   ", p2=" + p2 +
                   ", p3=" + p3 +
                   ", p4=" + p4 +
                   ", p5=" + p5 +
                   '}';
        }
    }
}
