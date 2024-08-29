package edu.escuelaing.arep;

import java.util.HashMap;
import java.util.Map;


public class Spark {

    private static Spark instance;
    private final Map<String, Service> getRoutes = new HashMap<>();
    private final Map<String, Service> postRoutes = new HashMap<>();

    public static Spark getInstance() {
        if (instance == null) {
            instance = new Spark();
        }
        return instance;
    }

    /**
     * Method to set the static file location.
     *
     * @param path The path of the static file location.
     */
    public static void staticFileLocation(String path) {
        HttpServer.getInstance().setStaticFileLocation(path);
    }

    /**
     * Method to define a GET route.
     *
     * @param path    The path of the route.
     * @param handler The handler of the route.
     */
    public static void get(String path, Service handler) {
        getInstance().getRoutes.put(path, handler);
    }

    /**
     * Method to find the handler of a route.
     *
     * @param method The method of the route.
     * @param path   The path of the route.
     * @return The handler of the route.
     */
    public static Service findHandler(String method, String path) {
        if ("GET".equalsIgnoreCase(method)) {
            return getInstance().getRoutes.get(path);
        } else {
            // Handle case where there is no handler for the method
            return null;
        }
    }

}
