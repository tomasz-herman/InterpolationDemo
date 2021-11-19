package pl.edu.pw.mini.cadcam.pusn.graphics;

import com.jogamp.opengl.GL4;
import org.joml.Matrix4f;
import pl.edu.pw.mini.cadcam.pusn.model.Model;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Scene {
    private Model model;
    private Interpolation interpolation;
    private boolean showKeyframes;
    private int frames;
    private final Camera camera;
    private float time;

    public Scene(Camera camera) {
        this.camera = camera;
    }

    public Stream<Model> getModels(BiFunction<Interpolation, Float, Matrix4f> interpolationFunction) {
        Stream<Model> modelStream = Stream.of(model);
        if(showKeyframes && frames > 0 && interpolationFunction != null && interpolation != null) {
            Stream<Model> framesStream = IntStream.rangeClosed(0, frames + 1)
                    .mapToObj(i -> {
                        float t = interpolation.getStartTime() + interpolation.getDuration() * (float)i / (frames + 1);
                        if(t > time) return null;
                        return model.setModelMatrix(interpolationFunction.apply(interpolation, t));
                    }).filter(Objects::nonNull);
            modelStream = Stream.concat(modelStream, framesStream);
        }
        return modelStream;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void dispose(GL4 gl) {
        model.dispose(gl);
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
