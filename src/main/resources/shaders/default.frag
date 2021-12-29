#version 330 core
out vec4 FragColor;

in VertexData {
    vec3 position;
    vec3 normal;
} fs_in;

uniform vec3 viewPos;
uniform vec3 color;

const vec3 lightDir = vec3(0, -1, 0);
const vec3 material = vec3(1, 1, 1);
const float specularStrength = 0.25;

void main() {
    vec3 normal = normalize(fs_in.normal);

    vec3 viewDir = normalize(viewPos - fs_in.position);
    vec3 reflectDir = reflect(lightDir, normal);

    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 16);

    float light = max(spec * specularStrength + dot(normal, -lightDir), 0.2);

    FragColor = vec4(material * light * color, 1.0f);
}