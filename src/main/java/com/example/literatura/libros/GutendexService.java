package com.example.literatura.libros;

import com.example.literatura.libros.model.Autor;
import com.example.literatura.libros.model.Datos;
import com.example.literatura.libros.model.Libro;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Service
public class GutendexService {
    private final ObjectMapper objectMapper;

    public GutendexService() {
        this.objectMapper = new ObjectMapper();
    }

    public Libro buscarLibro(String titulo) throws IOException {
        String encodedTitulo = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        String urlString = "https://gutendex.com/books/?search=" + encodedTitulo;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("HTTP GET Request Failed with Error code : " + conn.getResponseCode());
        }

        Scanner scanner = new Scanner(url.openStream());
        StringBuilder inline = new StringBuilder();
        while (scanner.hasNext()) {
            inline.append(scanner.nextLine());
        }
        scanner.close();

        JsonNode rootNode = objectMapper.readTree(inline.toString());
        JsonNode bookNode = rootNode.path("results").get(0);

        if (bookNode.isMissingNode()) {
            throw new RuntimeException("No se encontraron libros con el título especificado.");
        }

        Libro libro = new Libro();
        libro.setTitulo(bookNode.path("title").asText());

        JsonNode authorNode = bookNode.path("authors").get(0);
        Autor autor = new Autor();
        autor.setApellido(authorNode.path("last_name").asText());
        autor.setNombre(authorNode.path("first_name").asText());
        libro.setAutor(autor);

        Datos datos = new Datos(bookNode.path("languages").get(0).asText(), bookNode.path("download_count").asInt());
        libro.setDatos(datos);

        return libro;
    }
}