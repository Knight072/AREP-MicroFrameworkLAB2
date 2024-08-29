package edu.escuelaing.arep;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioHandler {

    private static final List<Usuario> usuarios = new ArrayList<>();

    static {
        usuarios.add(new Usuario("Juan", 28, "juan@example.com"));
        usuarios.add(new Usuario("Ana", 25, "ana@example.com"));
        usuarios.add(new Usuario("Luis", 30, "luis@example.com"));
    }

    public static void mostrarUsuarios(OutputStream out) throws IOException {
        Respuesta respuesta = new Respuesta("success", usuarios.toArray(new Usuario[0]));
        String jsonResponse = toJson(respuesta);
        sendResponse(out, "200 OK", "application/json", jsonResponse.getBytes());
    }

    private static String toJson(Object obj) {
        if (obj instanceof Respuesta) {
            Respuesta resp = (Respuesta) obj;
            StringBuilder sb = new StringBuilder();
            sb.append("{\"status\":\"").append(resp.status).append("\", \"usuarios\":[");
            for (Usuario usuario : resp.usuarios) {
                sb.append(toJson(usuario)).append(",");
            }
            if (resp.usuarios.length > 0) sb.setLength(sb.length() - 1); // Eliminar la última coma
            sb.append("]}");
            return sb.toString();
        } else if (obj instanceof Usuario) {
            Usuario usuario = (Usuario) obj;
            return String.format("{\"nombre\":\"%s\", \"edad\":%d, \"email\":\"%s\"}", usuario.nombre, usuario.edad, usuario.email);
        }
        return "{}";
    }

    private static void sendResponse(OutputStream out, String status, String contentType, byte[] content) throws IOException {
        // Enviar los encabezados HTTP
        PrintWriter writer = new PrintWriter(out);
        writer.println("HTTP/1.1 " + status);
        writer.println("Content-Type: " + contentType);
        writer.println("Content-Length: " + content.length);
        writer.println();
        writer.flush();

        // Enviar el contenido binario (incluidas imágenes, HTML, etc.)
        out.write(content);
        out.flush();
    }
}
