package edu.escuelaing.arep;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsuarioHandlerTest {

    @Test
    public void testMostrarUsuarios() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        UsuarioHandler.mostrarUsuarios(out);

        String response = out.toString("UTF-8");
        assertTrue(response.contains("\"status\":\"success\""));
        assertTrue(response.contains("\"usuarios\":"));
    }
}

