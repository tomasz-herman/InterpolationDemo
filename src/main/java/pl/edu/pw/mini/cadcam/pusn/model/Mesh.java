package pl.edu.pw.mini.cadcam.pusn.model;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.util.GLBuffers;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Mesh {
    private final float[] positions;
    private final float[] normals;
    private final int[] indices;
    private final IntBuffer vao;
    private final List<IntBuffer> vbos = new ArrayList<>();
    private final int primitivesType;

    public Mesh(float[] positions, float[] normals, int[] indices, int primitivesType) {
        this.positions = positions;
        this.indices = indices;
        this.normals = normals;
        this.vao = GLBuffers.newDirectIntBuffer(1);
        this.primitivesType = primitivesType;
    }

    public void load(GL4 gl) {
        gl.glGenVertexArrays(1, vao);
        gl.glBindVertexArray(vao.get(0));
        loadData(gl, positions, 0, 3);
        if(normals != null) loadData(gl, normals, 1, 3);
        loadIndices(gl, indices);
        gl.glBindVertexArray(0);
    }

    public void loadData(GL4 gl, float[] data, int index, int size) {
        IntBuffer vbo = GLBuffers.newDirectIntBuffer(1);
        gl.glGenBuffers(1, vbo);
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, vbo.get(0));
        gl.glBufferData(
                GL4.GL_ARRAY_BUFFER,
                (long) data.length * Float.BYTES,
                GLBuffers.newDirectFloatBuffer(data),
                GL4.GL_STATIC_DRAW);
        gl.glEnableVertexAttribArray(index);
        gl.glVertexAttribPointer(index, size, GL4.GL_FLOAT, false, 0, 0);
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
        vbos.add(vbo);
    }

    public void loadIndices(GL4 gl, int[] data) {
        IntBuffer vbo = GLBuffers.newDirectIntBuffer(1);
        gl.glGenBuffers(1, vbo);
        gl.glBindBuffer(GL4.GL_ELEMENT_ARRAY_BUFFER, vbo.get(0));
        gl.glBufferData(
                GL4.GL_ELEMENT_ARRAY_BUFFER,
                (long) data.length * Integer.BYTES,
                GLBuffers.newDirectIntBuffer(data),
                GL4.GL_STATIC_DRAW);
        gl.glBindBuffer(GL4.GL_ARRAY_BUFFER, 0);
        vbos.add(vbo);
    }

    public void dispose(GL4 gl) {
        gl.glDeleteVertexArrays(1, vao);
        for (IntBuffer vbo : vbos) {
            gl.glDeleteBuffers(1, vbo);
        }
    }

    public int getVao() {
        return vao.get(0);
    }

    public int getVbo(int i) {
        return vbos.get(i).get(0);
    }

    public int vertexCount() {
        return indices.length;
    }

    public int getPrimitivesType() {
        return primitivesType;
    }
}
