package pl.edu.pw.mini.cadcam.pusn.model;

import com.jogamp.opengl.GL4;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.BufferUtils.createByteBuffer;
import static org.lwjgl.assimp.Assimp.*;
import static org.lwjgl.system.MemoryUtil.*;

public class ModelLoader {
    private static final AIFileIO fileIO = createFileIO();

    public static Model load(String path, String resource) throws Exception {
        return load(path, resource, aiProcess_GenSmoothNormals | aiProcess_JoinIdenticalVertices |
                                    aiProcess_Triangulate | aiProcess_FixInfacingNormals | aiProcess_CalcTangentSpace |
                                    aiProcess_PreTransformVertices);
    }

    public static Model load(String path, String resource, int flags) throws Exception {
        Path resourcePath = Paths.get(path, resource);
        AIScene aiScene = aiImportFileEx(resourcePath.toString(), flags, fileIO);
        if (aiScene == null) {
            throw new Exception("Error loading model " + resourcePath);
        }

        int numMeshes = aiScene.mNumMeshes();
        PointerBuffer aiMeshes = aiScene.mMeshes();

        if (aiMeshes == null) {
            throw new Exception("Error loading model " + resourcePath);
        }

        List<Mesh> meshes = new ArrayList<>();
        for (int i = 0; i < numMeshes; i++) {
            AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));
            meshes.add(processMesh(aiMesh));
        }

        Model model = new Model(meshes);

        aiReleaseImport(aiScene);
        return model;
    }

    private static Mesh processMesh(AIMesh aiMesh) {
        float[] vertices = ArrayUtils.toPrimitive(processVertices(aiMesh).toArray(new Float[0]));
        float[] normals = ArrayUtils.toPrimitive(processNormals(aiMesh).toArray(new Float[0]));
        int[] indices = ArrayUtils.toPrimitive(processIndices(aiMesh).toArray(new Integer[0]));

        return new Mesh(vertices, normals, indices, GL4.GL_TRIANGLES);
    }

    private static List<Integer> processIndices(AIMesh aiMesh) {
        List<Integer> indices = new ArrayList<>();
        int numFaces = aiMesh.mNumFaces();
        AIFace.Buffer aiFaces = aiMesh.mFaces();
        for (int i = 0; i < numFaces; i++) {
            AIFace aiFace = aiFaces.get(i);
            IntBuffer buffer = aiFace.mIndices();
            while (buffer.remaining() > 0) {
                indices.add(buffer.get());
            }
        }
        return indices;
    }

    private static List<Float> processNormals(AIMesh aiMesh) {
        List<Float> normals = new ArrayList<>();
        AIVector3D.Buffer aiNormals = aiMesh.mNormals();
        int numTextCoords = aiNormals != null ? aiNormals.remaining() : 0;
        for (int i = 0; i < numTextCoords; i++) {
            AIVector3D normal = aiNormals.get();
            normals.add(normal.x());
            normals.add(normal.y());
            normals.add(normal.z());
        }
        return normals;
    }

    private static List<Float> processVertices(AIMesh aiMesh) {
        List<Float> vertices = new ArrayList<>();
        AIVector3D.Buffer aiVertices = aiMesh.mVertices();
        while (aiVertices.remaining() > 0) {
            AIVector3D aiVertex = aiVertices.get();
            vertices.add(aiVertex.x());
            vertices.add(aiVertex.y());
            vertices.add(aiVertex.z());
        }
        return vertices;
    }

    private static AIFileIO createFileIO() {
        return AIFileIO.create()
                .OpenProc((pFileIO, fileName, openMode) -> {
                    try {
                        ByteBuffer data = read(memUTF8(fileName));
                        return AIFile.create()
                                .ReadProc((pFile, pBuffer, size, count) -> {
                                    long max = Math.min(data.remaining(), size * count);
                                    memCopy(memAddress(data) + data.position(), pBuffer, max);
                                    return max;
                                })
                                .SeekProc((pFile, offset, origin) -> {
                                    if (origin == aiOrigin_CUR) {
                                        data.position(data.position() + (int) offset);
                                    } else if (origin == aiOrigin_SET) {
                                        data.position((int) offset);
                                    } else if (origin == aiOrigin_END) {
                                        data.position(data.limit() + (int) offset);
                                    }
                                    return 0;
                                })
                                .FileSizeProc(pFile -> data.limit())
                                .address();
                    } catch (IOException e) {
                        throw new RuntimeException("Could not open file: " + memUTF8(fileName));
                    }

                })
                .CloseProc((pFileIO, pFile) -> {
                    AIFile aiFile = AIFile.create(pFile);
                    aiFile.ReadProc().free();
                    aiFile.SeekProc().free();
                    aiFile.FileSizeProc().free();
                });
    }

    private static ByteBuffer read(String resource) throws IOException {
        if(resource == null) throw new IOException("Resource is null.");

        Path path = Paths.get(resource);
        byte[] bytes;

        if (Files.isReadable(path)) {
            bytes = Files.readAllBytes(path);
        } else {
            try (InputStream is = ModelLoader.class.getResourceAsStream(resource)) {
                if(is == null) throw new IOException("InputStream is null.");
                bytes = is.readAllBytes();
            }
        }

        return createByteBuffer(bytes.length)
                .put(bytes)
                .flip();
    }
}
