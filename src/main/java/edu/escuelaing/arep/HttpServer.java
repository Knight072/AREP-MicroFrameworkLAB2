package edu.escuelaing.arep;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final int port;
    private final ExecutorService threadPool;
    private String baseStaticFileURI;
    private static HttpServer instance;

    public HttpServer() {
        this.port = 8080;
        this.threadPool = Executors.newFixedThreadPool(10);
    }

    public static HttpServer getInstance() {
        if (instance == null) {
            instance = new HttpServer();
        }
        return instance;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Servidor escuchando en el puerto " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            threadPool.submit(new ClientHandler(clientSocket, baseStaticFileURI));
        }
    }

    public void setStaticFileLocation(String path) {
        baseStaticFileURI = path;
    }
}
