package edu.escuelaing.arep;

import java.io.IOException;
import java.io.OutputStream;

public interface Service {
    void handle(OutputStream out, String resource) throws IOException;

}
