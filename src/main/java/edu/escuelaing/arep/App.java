package edu.escuelaing.arep;

import java.io.IOException;
import java.io.OutputStream;

public class App {
    public static void main(String[] args) {
        // Set the static file location
        Spark.staticFileLocation("target/classes/public");

        // Define the services
        Spark.get("/App/leerArchivo?nombre", (out, file) -> {
            try {
                FileHandler.mostrarArchivo(out, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Spark.get("/App/usuarios", (out, file) -> {
            UsuarioHandler.mostrarUsuarios(out);
        });

        try {
            HttpServer server = HttpServer.getInstance();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
