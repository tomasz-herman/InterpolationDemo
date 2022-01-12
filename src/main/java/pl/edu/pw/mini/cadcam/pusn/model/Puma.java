package pl.edu.pw.mini.cadcam.pusn.model;

import com.jogamp.opengl.GL4;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.*;
import pl.edu.pw.mini.cadcam.pusn.graphics.Camera;
import pl.edu.pw.mini.cadcam.pusn.graphics.Renderer;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Math.*;

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
    private final Model xe;
    private final Model ye;
    private final Model ze;
    private final Model x2;
    private final Model y2;
    private final Model z2;
    private final List<Model> models;

    private double startTime;
    private double endTime;
    private final Vector3d startPosition = new Vector3d();
    private final Vector3d startRotation = new Vector3d();
    private final Vector3d endPosition = new Vector3d();
    private final Vector3d endRotation = new Vector3d();

    private double l1, l2, l3, l4;
    private double p1, p2, p3, p4, p5;

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
        xe = ModelLoader.load("/models/", "cylinder.obj");
        xe.setColor(new Vector3f(1, 0, 0));
        ye = ModelLoader.load("/models/", "cylinder.obj");
        ye.setColor(new Vector3f(0, 1, 0));
        ze = ModelLoader.load("/models/", "cylinder.obj");
        ze.setColor(new Vector3f(0, 0.5f, 1));
        models = List.of(j1, a1, j2, a2, j3, a3, j4, a4, x1, y1, z1, x2, y2, z2, xe, ye, ze/**/);
        l1 = l2 = l3 = l4 = 3;
        p1 = p2 = p3 = p4 = p5 = 0;
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
                .translate(startPosition)
                .rotateXYZ(startRotation)
                .rotateZ(-PI / 2)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        x1.setModelMatrix(new Matrix4f(mx1));

        Matrix4d my1 = new Matrix4d(model)
                .translate(startPosition)
                .rotateXYZ(startRotation)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        y1.setModelMatrix(new Matrix4f(my1));

        Matrix4d mz1 = new Matrix4d(model)
                .translate(startPosition)
                .rotateXYZ(startRotation)
                .rotateX(PI / 2)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        z1.setModelMatrix(new Matrix4f(mz1));

        Matrix4d mx2 = new Matrix4d(model)
                .translate(endPosition)
                .rotateXYZ(endRotation)
                .rotateZ(-PI / 2)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        x2.setModelMatrix(new Matrix4f(mx2));

        Matrix4d my2 = new Matrix4d(model)
                .translate(endPosition)
                .rotateXYZ(endRotation)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        y2.setModelMatrix(new Matrix4f(my2));

        Matrix4d mz2 = new Matrix4d(model)
                .translate(endPosition)
                .rotateXYZ(endRotation)
                .rotateX(PI / 2)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        z2.setModelMatrix(new Matrix4f(mz2));

        model.rotateZ(p1);
        Matrix4d mj1 = new Matrix4d(model)
                .rotateX(PI / 2)
                .scale(0.2f, 0.2f, 0.2f)
                .translate(0, 1, 0);
        j1.setModelMatrix(new Matrix4f(mj1));
        Matrix4d ma1 = new Matrix4d(model)
                .rotateX(PI / 2)
                .scale(0.1f, 0.5f * l1, 0.1f)
                .translate(0, 1, 0);
        a1.setModelMatrix(new Matrix4f(ma1));


        model.translate(0, 0, l1).rotateY(p2);

        Matrix4d mj2 = new Matrix4d(model)
                .rotateZ(-PI / 2)
                .scale(0.2f, 0.2f, 0.2f)
                .rotateZ(PI / 2);
        j2.setModelMatrix(new Matrix4f(mj2));
        Matrix4d ma2 = new Matrix4d(model)
                .rotateZ(-PI / 2)
                .scale(0.1f, 0.5f * l2, 0.1f)
                .translate(0, 1, 0);
        a2.setModelMatrix(new Matrix4f(ma2));

        model.translate(l2, 0, 0).rotateY(p3);

        Matrix4d mj3 = new Matrix4d(model)
                .rotateX(-PI / 2)
                .scale(0.2f, 0.2f, 0.2f)
                .rotateX(PI / 2);
        j3.setModelMatrix(new Matrix4f(mj3));
        Matrix4d ma3 = new Matrix4d(model)
                .rotateX(-PI / 2)
                .scale(0.1f, 0.5f * l3, 0.1f)
                .translate(0, 1, 0);
        a3.setModelMatrix(new Matrix4f(ma3));

        model.translate(0, 0, -l3).rotateZ(p4);

        Matrix4d mj4 = new Matrix4d(model)
                .rotateZ(-PI / 2)
                .scale(0.2f, 0.2f, 0.2f);
        j4.setModelMatrix(new Matrix4f(mj4));

        Matrix4d ma4 = new Matrix4d(model)
                .rotateZ(-PI / 2)
                .scale(0.1f, 0.5f * l4, 0.1f)
                .translate(0, 1, 0);
        a4.setModelMatrix(new Matrix4f(ma4));

        model.translate(l4, 0, 0).rotateX(p5);

        Matrix4d mxe = new Matrix4d(model)
                .rotateZ(-PI / 2)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        xe.setModelMatrix(new Matrix4f(mxe));

        Matrix4d mye = new Matrix4d(model)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        ye.setModelMatrix(new Matrix4f(mye));

        Matrix4d mze = new Matrix4d(model)
                .rotateX(PI / 2)
                .scale(0.04f, 0.25f, 0.04f)
                .translate(0, 1, 0);
        ze.setModelMatrix(new Matrix4f(mze));
    }

    public double getL1() {
        return l1;
    }

    public void setL1(double l1) {
        this.l1 = l1;
    }

    public double getL2() {
        return l2;
    }

    public void setL2(double l2) {
        this.l2 = l2;
    }

    public double getL3() {
        return l3;
    }

    public void setL3(double l3) {
        this.l3 = l3;
    }

    public double getL4() {
        return l4;
    }

    public void setL4(double l4) {
        this.l4 = l4;
    }

    public double getP1() {
        return p1;
    }

    public void setP1(double p1) {
        this.p1 = p1;
    }

    public double getP2() {
        return p2;
    }

    public void setP2(double p2) {
        this.p2 = p2;
    }

    public double getP3() {
        return p3;
    }

    public void setP3(double p3) {
        this.p3 = p3;
    }

    public double getP4() {
        return p4;
    }

    public void setP4(double p4) {
        this.p4 = p4;
    }

    public double getP5() {
        return p5;
    }

    public void setP5(double p5) {
        this.p5 = p5;
    }

    public Vector3d forwardKinematics(Parameters p) {
        Matrix4d model = new Matrix4d();
        model.rotateZ(p.p1);
        model.translate(0, 0, l1).rotateY(p.p2);
        model.translate(p.l2, 0, 0).rotateY(p.p3);
        model.translate(0, 0, -l3).rotateZ(p.p4);
        model.translate(l4, 0, 0).rotateX(p.p5);

        return model.getTranslation(new Vector3d());
    }

//    public Parameters inverseKinematics(Vector3d translation, Matrix3d rotation, Parameters hint) {
//        Parameters r = new Parameters();
//        Vector3d x = rotation.getColumn(0, new Vector3d());
//        Vector3d y = rotation.getColumn(1, new Vector3d());
//        Vector3d z = rotation.getColumn(2, new Vector3d());
//        double x1 = x.x, x2 = x.y, x3 = x.z;
//        double y1 = y.x, y2 = y.y, y3 = y.z;
//        double z1 = z.x, z2 = z.y, z3 = z.z;
//        double p1 = translation.x, p2 = translation.y, p3 = translation.z;
//
//        if(hint == null) {
//            if(new Vector2d(p2 - l4 * x2, p1 - l4 * x1).length() < 1e-3) {
//                translation.add(disturbance());
//                return inverseKinematics(translation, rotation, null);
//            } else {
//                r.p1 = arctan((p2 - l4 * x2) / (p1 - l4 * x1), PI / 2);
//            }
//        } else {
//            if(new Vector2d(p2 - l4 * x2, p1 - l4 * x1).length() < 1e-3) {
//                translation.add(disturbance());
//                return inverseKinematics(translation, rotation, hint);
//            } else {
//                r.p1 = arctan((p2 - l4 * x2) / (p1 - l4 * x1), PI + hint.p1);
//            }
//        }
//        double s1 = sin(r.p1), c1 = cos(r.p1);
//
//        if(hint == null) {
//            r.p4 = asin(c1 * x2 - s1 * x1);
//        } else {
//            r.p4 = arcsin((c1 * p2 - s1 * p1) / l4, hint.p4);
//        }
//        double s4 = sin(r.p4), c4 = cos(r.p4);
//
//        if(hint != null) r.p5 = arctan((s1 * z1 - c1 * z2) / (c1 * y2 - s1 * y1), hint.p5);
//        else r.p5 = atan2((s1 * z1 - c1 * z2), (c1 * y2 - s1 * y1)); // one solution
//
//        if(hint == null) {
//            if(new Vector2d(c1 * c4 * (p3 - l4 * x3 - l1) + l3 * (x1 + s1 * s4), (c4 * (p1 - l4 * x1) - c1 * l3 * x3)).length() < 1e-3) {
//                translation.add(disturbance());
//                return inverseKinematics(translation, rotation, null);
//            }
//            else r.p2 = atan(-(c1 * c4 * (p3 - l4 * x3 - l1) + l3 * (x1 + s1 * s4)) / (c4 * (p1 - l4 * x1) - c1 * l3 * x3));
//        } else {
//            if(new Vector2d(c1 * c4 * (p3 - l4 * x3 - l1) + l3 * (x1 + s1 * s4), c4 * (p1 - l4 * x1) - c1 * l3 * x3).length() < 1e-3) {
//                translation.add(disturbance());
//                return inverseKinematics(translation, rotation, hint);
//            } else {
//                r.p2 = atan(-(c1 * c4 * (p3 - l4 * x3 - l1) + l3 * (x1 + s1 * s4)) / (c4 * (p1 - l4 * x1) - c1 * l3 * x3));
//            }
//        }
//        double s2 = sin(r.p2), c2 = cos(r.p2);
//
//        r.l2 = (c4 * (p1 - l4 * x1) - c1 * l3 * x3) / (c1 * c2 * c4); // one solution
//
//        if(hint != null && abs(r.l2 - hint.l2) > 1) {
//            hint.p1 = PI + hint.p1;
//            if(hint.p1 > 2 * PI) hint.p1 -= 2 * PI;
//            return hint;
//        }
//
//        r.p3 = atan2(-x3, (x.x + s1 * s4) / c1) - r.p2; // one solution
//
//        return r;
//    }

        public Parameters inverseKinematics(Vector3d translation, Matrix3d rotation) {
            List<Parameters> r = new ArrayList<>();
            Vector3d x = rotation.getColumn(0, new Vector3d());
            Vector3d y = rotation.getColumn(1, new Vector3d());
            Vector3d z = rotation.getColumn(2, new Vector3d());
            double x1 = x.x, x2 = x.y, x3 = x.z;
            double y1 = y.x, y2 = y.y, y3 = y.z;
            double z1 = z.x, z2 = z.y, z3 = z.z;
            double p1 = translation.x, p2 = translation.y, p3 = translation.z;


            var a1 = arctan2((p2 - l4 * x2) / (p1 - l4 * x1));
            Parameters params = new Parameters();
            params.setP1(a1.getRight());
            r.add(params);
            params = new Parameters();
            params.setP1(a1.getLeft());
            r.add(params);

            List<Parameters> r2 = new ArrayList<>();

            for (Parameters p : r) {
                var a4 = arcsin2((p.c1 * p2 - p.s1 * p1) / l4);
                params = p.copy();
                params.setP4(a4.getRight());
                r2.add(params);
                params = p.copy();
                params.setP4(a4.getLeft());
                r2.add(params);
            }

            for (Parameters p : r2) {
                p.setP5(atan2((p.s1 * z1 - p.c1 * z2), (p.c1 * y2 - p.s1 * y1)));
            }


            List<Parameters> r3 = new ArrayList<>();
            for (Parameters p : r2) {
                var a2 = arctan2(-(p.c1 * p.c4 * (p3 - l4 * x3 - l1) + l3 * (x1 + p.s1 * p.s4)) / (p.c4 * (p1 - l4 * x1) - p.c1 * l3 * x3));
                params = p.copy();
                params.setP2(a2.getRight());
                r3.add(params);
                params = p.copy();
                params.setP2(a2.getLeft());
                r3.add(params);
            }

            for (Parameters p : r3) {
                p.setL2(abs((p.c4 * (p1 - l4 * x1) - p.c1 * l3 * x3) / (p.c1 * p.c2 * p.c4)));
                p.setP3(atan2(-x3, (x.x + p.s1 * p.s4) / p.c1) - p.p2);
            }

            for (Parameters p : r3) {
                if(!Double.isFinite(forwardKinematics(p).distance(translation)) || forwardKinematics(p).distance(translation) > 0.1) continue;
                return p;
            }

            translation.add(disturbance());
            return inverseKinematics(translation, rotation);
    }

    public Parameters inverseKinematics(Vector3d translation, Matrix3d rotation, Parameters hint) {
        List<Parameters> r = new ArrayList<>();
        Vector3d x = rotation.getColumn(0, new Vector3d());
        Vector3d y = rotation.getColumn(1, new Vector3d());
        Vector3d z = rotation.getColumn(2, new Vector3d());
        double x1 = x.x, x2 = x.y, x3 = x.z;
        double y1 = y.x, y2 = y.y, y3 = y.z;
        double z1 = z.x, z2 = z.y, z3 = z.z;
        double p1 = translation.x, p2 = translation.y, p3 = translation.z;


        var a1 = arctan2((p2 - l4 * x2) / (p1 - l4 * x1));
        Parameters params = new Parameters();
        params.setP1(a1.getRight());
        r.add(params);
        params = new Parameters();
        params.setP1(a1.getLeft());
        r.add(params);

        List<Parameters> r2 = new ArrayList<>();

        for (Parameters p : r) {
            var a4 = arcsin2((p.c1 * p2 - p.s1 * p1) / l4);
            params = p.copy();
            params.setP4(a4.getRight());
            r2.add(params);
            params = p.copy();
            params.setP4(a4.getLeft());
            r2.add(params);
        }

        for (Parameters p : r2) {
            p.setP5(atan2((p.s1 * z1 - p.c1 * z2), (p.c1 * y2 - p.s1 * y1)));
        }


        List<Parameters> r3 = new ArrayList<>();
        for (Parameters p : r2) {
            var a2 = arctan2(-(p.c1 * p.c4 * (p3 - l4 * x3 - l1) + l3 * (x1 + p.s1 * p.s4)) / (p.c4 * (p1 - l4 * x1) - p.c1 * l3 * x3));
            params = p.copy();
            params.setP2(a2.getRight());
            r3.add(params);
            params = p.copy();
            params.setP2(a2.getLeft());
            r3.add(params);
        }

        List<Parameters> r4 = new ArrayList<>();
        for (Parameters p : r3) {
            p.setL2((p.c4 * (p1 - l4 * x1) - p.c1 * l3 * x3) / (p.c1 * p.c2 * p.c4));
            params = p.copy();
            params.setP3(atan2(-x3, (x.x + p.s1 * p.s4) / p.c1) - p.p2);
            r4.add(params);
            params = p.copy();
            params.setP3(-atan2(-x3, (x.x + p.s1 * p.s4) / p.c1) - p.p2);
            r4.add(params);
        }

        Parameters result = null;
        double bestDist = Double.POSITIVE_INFINITY;
        for (Parameters p : r4) {
            if(!Double.isFinite(forwardKinematics(p).distance(translation)) || forwardKinematics(p).distance(translation) > 0.2) continue;
            double dist = p.dist(hint);
            if(dist < bestDist) {
                bestDist = dist;
                result = p;
            }
        }
        if(result == null) return hint;

        return result;
    }


    public static Vector3d disturbance() {
        Random random = new Random();
        return new Vector3d((random.nextFloat() - 1) * 1e-3f, (random.nextFloat() - 1) * 1e-3f, (random.nextFloat() - 1) * 1e-3f);
    }

    public static Vector3d disturbance(double mag) {
        Random random = new Random();
        return new Vector3d((random.nextFloat() - 1) * mag, (random.nextFloat() - 1) * mag, (random.nextFloat() - 1) * mag);
    }

    public double arctan(double val, double hint) {
        double t1 = atan(val);
        double t2 = t1 + PI;
        if(angleDist(t1, hint) < angleDist(t2, hint)) return t1;
        else return t2;
    }

    public Pair<Double, Double> arctan2(double val) {
        double t1 = atan(val);
        double t2 = t1 + PI;
        return Pair.of(t1, t2);
    }

    public double arcsin(double val, double hint) {
        double t1 = asin(val);
        double t2 = PI - t1;
        if(angleDist(t1, hint) < angleDist(t2, hint)) return t1;
        else return t2;
    }

    public Pair<Double, Double> arcsin2(double val) {
        double t1 = asin(val);
        double t2 = PI - t1;
        return Pair.of(t1, t2);
    }

    public static double angleDist(double a, double b) {
        if (a < 0) a += 2 * PI;
        if (b < 0) b += 2 * PI;
        if (b - a > PI) b -= 2 * PI;
        else if (a - b > PI) a -= 2 * PI;
        return Math.min(abs(a - b), abs(b - a));
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

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public Vector3d getStartPosition() {
        return startPosition;
    }

    public Vector3d getStartRotation() {
        return startRotation;
    }

    public Vector3d getEndPosition() {
        return endPosition;
    }

    public Vector3d getEndRotation() {
        return endRotation;
    }

    public void setStartPosition(Vector3d position) {
        startPosition.set(position);
    }

    public void setStartRotation(Vector3d rotation) {
        startRotation.set(rotation);
    }

    public void setEndPosition(Vector3d position) {
        endPosition.set(position);
    }

    public void setEndRotation(Vector3d rotation) {
        endRotation.set(rotation);
    }

    public Parameters interpolateEffector(double time, Parameters hint) {
        double t = getStep(time);
        if(hint != null && t == 1){
            set(hint);
            return hint;
        }
        Quaterniond startQuaternion = new Quaterniond()
                .rotateXYZ(startRotation.x, startRotation.y, startRotation.z);
        Quaterniond endQuaternion = new Quaterniond()
                .rotateXYZ(endRotation.x, endRotation.y, endRotation.z);
        Quaterniond quaternion = startQuaternion.slerp(endQuaternion, t, new Quaterniond());
        Vector3d position = startPosition.lerp(endPosition, t, new Vector3d());
        Matrix3d rotation = new Matrix3d().rotation(quaternion);
        Parameters parameters;
        if(hint == null) parameters = inverseKinematics(position, rotation);
        else parameters = inverseKinematics(position, rotation, hint);
        System.out.println(parameters);
        set(parameters);
        return parameters;
    }

    public void interpolateParameters(double time) {
        double t = getStep(time);
        Quaterniond startQuaternion = new Quaterniond()
                .rotateXYZ(startRotation.x, startRotation.y, startRotation.z);
        Quaterniond endQuaternion = new Quaterniond()
                .rotateXYZ(endRotation.x, endRotation.y, endRotation.z);
        Parameters start = inverseKinematics(startPosition, new Matrix3d().rotate(startQuaternion));
        Parameters end = inverseKinematics(endPosition, new Matrix3d().rotate(endQuaternion));
        Parameters lerp = start.lerp(end, t, new Parameters());
        set(lerp);
    }

    public double getStep(double time) {
        double t = (time - startTime) / (endTime - startTime);
        if (t > 1) t = 1;
        if (t < 0) t = 0;
        return t;
    }

    public static class Parameters {
        public double l2;
        public double p1, p2, p3, p4, p5;
        public double c1, c2, c3, c4, c5;
        public double s1, s2, s3, s4, s5;

        public double dist(Parameters p) {
            return abs(l2 - p.l2) +
                    angleDist(p1, p.p1) +
                    angleDist(p2, p.p2) +
                    angleDist(p3, p.p3) +
                    angleDist(p4, p.p4) +
                    angleDist(p5, p.p5);
        }

        public void setL2(double l2) {
            this.l2 = l2;
        }

        public void setP1(double p1) {
            this.p1 = p1;
            this.c1 = cos(p1);
            this.s1 = sin(p1);
        }

        public void setP2(double p2) {
            this.p2 = p2;
            this.c2 = cos(p2);
            this.s2 = sin(p2);
        }

        public void setP3(double p3) {
            this.p3 = p3;
            this.c3 = cos(p3);
            this.s3 = sin(p3);
        }

        public void setP4(double p4) {
            this.p4 = p4;
            this.c4 = cos(p4);
            this.s4 = sin(p4);
        }

        public void setP5(double p5) {
            this.p5 = p5;
            this.c5 = cos(p5);
            this.s5 = sin(p5);
        }

        public Parameters copy() {
            Parameters params = new Parameters();
            params.p1 = p1;
            params.p2 = p2;
            params.p3 = p3;
            params.p4 = p4;
            params.p5 = p5;
            params.c1 = c1;
            params.c2 = c2;
            params.c3 = c3;
            params.c4 = c4;
            params.c5 = c5;
            params.s1 = s1;
            params.s2 = s2;
            params.s3 = s3;
            params.s4 = s4;
            params.s5 = s5;
            params.l2 = l2;
            return params;
        }

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

        public Parameters lerp(Parameters parameters, double t, Parameters result) {
            result.l2 = lerpFloat(l2, parameters.l2, t);
            result.p1 = lerpAngle(p1, parameters.p1, t);
            result.p2 = lerpAngle(p2, parameters.p2, t);
            result.p3 = lerpAngle(p3, parameters.p3, t);
            result.p4 = lerpAngle(p4, parameters.p4, t);
            result.p5 = lerpAngle(p5, parameters.p5, t);
            return result;
        }

        public double lerpFloat(double a, double b, double t) {
            return a * (1 - t) + b * t;
        }

        public double lerpAngle(double a, double b, double t) {
            if (a < 0) a += 2 * PI;
            if (b < 0) b += 2 * PI;
            if (b - a > PI) b -= 2 * PI;
            if (a - b > PI) a -= 2 * PI;
            return a * (1 - t) + b * t;
        }
    }
}
