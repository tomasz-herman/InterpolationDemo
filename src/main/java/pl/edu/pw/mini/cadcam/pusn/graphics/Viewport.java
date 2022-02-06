package pl.edu.pw.mini.cadcam.pusn.graphics;

public class Viewport {
    private final int x, y, width, height;

    public Viewport(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Viewport left() {
        return new Viewport(x, y, width / 2, height);
    }

    public Viewport full() {
        return new Viewport(x, y, width, height);
    }

    public Viewport right() {
        return new Viewport(x + width / 2, y, width / 2, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Viewport{" +
               "x=" + x +
               ", y=" + y +
               ", width=" + width +
               ", height=" + height +
               '}';
    }
}
