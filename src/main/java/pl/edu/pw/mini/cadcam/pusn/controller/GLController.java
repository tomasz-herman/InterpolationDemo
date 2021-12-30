package pl.edu.pw.mini.cadcam.pusn.controller;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import pl.edu.pw.mini.cadcam.pusn.graphics.*;
import pl.edu.pw.mini.cadcam.pusn.graphics.Renderer;
import pl.edu.pw.mini.cadcam.pusn.model.Model;
import pl.edu.pw.mini.cadcam.pusn.model.ModelLoader;
import pl.edu.pw.mini.cadcam.pusn.model.Puma;

import javax.swing.*;
import java.awt.event.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class GLController implements GLEventListener, MouseListener, MouseWheelListener, MouseMotionListener {
    private final GLJPanel gljPanel;
    private GLContext context;
    private Renderer renderer;
    private Viewport viewport;
    private Scene scene;
    private Puma model;

    private final Vector2i lastMousePosition = new Vector2i();
    private Interpolation interpolation;
    private float time = 0;
    private float last = 0;

    private BiFunction<Interpolation, Float, Matrix4f> leftInterpolation = Interpolation::interpolateEuler;
    private BiFunction<Interpolation, Float, Matrix4f> rightInterpolation = Interpolation::interpolateSpherical;

    private boolean forward;
    private boolean backward;
    private boolean right;
    private boolean left;
    private boolean up;
    private boolean down;

    public GLController(GLJPanel gljPanel) {
        this.gljPanel = gljPanel;
        gljPanel.addGLEventListener(this);

        FPSAnimator animator = new FPSAnimator(gljPanel, 60, true);
        animator.start();

        gljPanel.addMouseListener(this);
        gljPanel.addMouseWheelListener(this);
        gljPanel.addMouseMotionListener(this);
        installKeyListener(gljPanel);

        gljPanel.setFocusable(true);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        context = drawable.getContext();

        renderer = new Renderer(gl);
        scene = new Scene(new Camera(1, 0.1f, 100, 60));
        scene.getCamera().setPosition(-10, 2, 0);

        interpolation = new Interpolation(0, 0,
                new Vector3f(0, 0, 0), new Vector3f(0, 0, 0),
                new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));

        try {
            scene.setModel(model = new Puma());
        } catch (Exception e) {
            e.printStackTrace();
        }

        last = System.nanoTime() / 1e9f;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL4 gl = drawable.getGL().getGL4();
        scene.dispose(gl);
        renderer.dispose(gl);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        float now = System.nanoTime() / 1e9f;
        time += now - last;
        last = now;
        GL4 gl = drawable.getGL().getGL4();
        handleKeyInput();

        renderer.clearColorAndDepth(gl);

        model.interpolateEffector(time);
        renderer.render(gl, scene, viewport.left());

        model.interpolateParameters(time);
        renderer.render(gl, scene, viewport.right());
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL4 gl = drawable.getGL().getGL4();
        viewport = new Viewport(x, y, width, height);
    }

    private void handleKeyInput() {
        float cameraSpeed = 0.1f;
        if(forward) scene.getCamera().move(0, 0, cameraSpeed);
        if(backward) scene.getCamera().move(0, 0, -cameraSpeed);
        if(left) scene.getCamera().move(-cameraSpeed, 0, 0);
        if(right) scene.getCamera().move(cameraSpeed, 0, 0);
        if(up) scene.getCamera().move(0, cameraSpeed, 0);
        if(down) scene.getCamera().move(0, -cameraSpeed, 0);
    }

    private void installKeyListener(GLJPanel gljPanel) {
        addAction(gljPanel, "pressed S", () -> backward = true);
        addAction(gljPanel, "released S", () -> backward = false);
        addAction(gljPanel, "pressed W", () -> forward = true);
        addAction(gljPanel, "released W", () -> forward = false);

        addAction(gljPanel, "pressed A", () -> left = true);
        addAction(gljPanel, "released A", () -> left = false);
        addAction(gljPanel, "pressed D", () -> right = true);
        addAction(gljPanel, "released D", () -> right = false);

        addAction(gljPanel, "pressed E", () -> up = true);
        addAction(gljPanel, "released E", () -> up = false);
        addAction(gljPanel, "pressed Q", () -> down = true);
        addAction(gljPanel, "released Q", () -> down = false);
    }

    private void addAction(JComponent component, String keyStroke, Runnable action) {
        component.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(keyStroke), keyStroke);
        component.getActionMap().put(keyStroke, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    private void runOnOpenGL(Consumer<GL4> action) throws GLException {
        context.makeCurrent();
        try {
            action.accept(context.getGL().getGL4());
        } finally {
            context.release();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gljPanel.requestFocus();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        gljPanel.requestFocus();
        if(SwingUtilities.isLeftMouseButton(e)) {
            lastMousePosition.set(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gljPanel.requestFocus();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        gljPanel.requestFocus();
        if(SwingUtilities.isLeftMouseButton(e)) {
            Vector2i mouseMove = new Vector2i(e.getX(), e.getY())
                    .sub(lastMousePosition);
            lastMousePosition.set(e.getX(), e.getY());
            float mouseSensitivity = 0.1f;
            scene.getCamera().rotate(mouseMove.x * mouseSensitivity, -mouseMove.y * mouseSensitivity, 0);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scene.getCamera().setFov(Math.clamp(1f, 179f,
                scene.getCamera().getFov() + 0.5f * e.getWheelRotation()));
    }

    public void setAnimation(Interpolation interpolation) {
        time = 0.0f;
        model.setStartPosition(interpolation.startPosition());
        model.setEndPosition(interpolation.endPosition());
        model.setStartRotation(interpolation.startRotation());
        model.setEndRotation(interpolation.endRotation());
        model.setStartTime(interpolation.startTime());
        model.setEndTime(interpolation.endTime());
    }

    public void setShowKeyframes(boolean show, int frames) {
        scene.setShowKeyframes(show);
        scene.setFrames(frames);
    }
}
