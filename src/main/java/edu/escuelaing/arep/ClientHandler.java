package edu.escuelaing.arep;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private String files;


    public ClientHandler(Socket clientSocket, String staticFiles) {
        this.clientSocket = clientSocket;
        this.files = staticFiles;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream out = clientSocket.getOutputStream()) {

            String requestLine = in.readLine();
            if (requestLine == null) return;

            // Parsear la solicitud
            System.out.println(requestLine);
            String method = requestLine.split(" ")[0];
            String resource = requestLine.split(" ")[1];

            callService(out, method, resource);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void callService(OutputStream out, String method, String resource) throws IOException {
        // Lógica de manejo de solicitudes
        if (method.equals("GET")) {
            if (resource.startsWith("/App/leerArchivo?nombre=")) {
                String fileName = resource.split("=")[1];
                System.out.println(resource.split("=")[0]);
                Service service = Spark.findHandler(method, resource.split("=")[0]);
                service.handle(out, fileName);
            } else if (resource.equals("/App/usuarios")) {
                String fileName = "";
                Service service = Spark.findHandler(method, resource);
                service.handle(out, fileName);
            } else {
                FileHandler.manejarArchivosEstaticos(out, resource);
            }
        } else {
            sendResponse(out, "405 Method Not Allowed", "text/plain", "Método no permitido".getBytes());
        }
    }

    private void sendResponse(OutputStream out, String status, String contentType, byte[] content) throws IOException {
        PrintWriter writer = new PrintWriter(out);
        writer.println("HTTP/1.1 " + status);
        writer.println("Content-Type: " + contentType);
        writer.println("Content-Length: " + content.length);
        writer.println();
        writer.flush();

        out.write(content);
        out.flush();
    }
}
