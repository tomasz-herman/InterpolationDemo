package pl.edu.pw.mini.cadcam.pusn.graphics;

import com.jogamp.opengl.GL4;
import pl.edu.pw.mini.cadcam.pusn.model.Model;
import pl.edu.pw.mini.cadcam.pusn.model.Renderable;


import java.util.stream.Stream;

public class Scene {
    private Renderable puma;
    private Interpolation interpolation;
    private boolean showKeyframes;
    private int frames;
    private final Camera camera;
    private float time;

    public Scene(Camera camera) {
        this.camera = camera;
    }

    public Stream<Renderable> getModels() {
        return Stream.of(puma);
    }

    public void setModel(Renderable puma) {
        this.puma = puma;
    }

    public void dispose(GL4 gl) {
        puma.dispose(gl);
    }

    public Camera getCamera() {
        return camera;
    }

    public void setInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
    }

    public void setShowKeyframes(boolean showKeyframes) {
        this.showKeyframes = showKeyframes;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
