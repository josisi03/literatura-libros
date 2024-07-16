package com.example.literatura.libros.model;

import com.example.literatura.libros.AutorService;
import com.example.literatura.libros.GutendexService;
import com.example.literatura.libros.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;

@Component
public class Principal implements CommandLineRunner {

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private GutendexService gutendexService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Listar todos los libros");
            System.out.println("3. Listar todos los autores");
            System.out.println("4. Listar autores vivos en un determinado año");
            System.out.println("5. Listar libros por idioma");
            System.out.println("0. Salir");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Ingrese el título del libro: ");
                    String titulo = scanner.nextLine();
                    try {
                        Libro libro = gutendexService.buscarLibro(titulo);
                        libroService.saveLibro(libro);
                        System.out.println("Libro guardado:");
                        System.out.println("Título: " + libro.getTitulo());
                        System.out.println("Autor: " + libro.getAutor().getNombre() + " " + libro.getAutor().getApellido());
                        System.out.println("Idioma: " + libro.getDatos().getIdioma());
                        System.out.println("Número de descargas: " + libro.getDatos().getDescargas());
                    } catch (IOException e) {
                        System.out.println("Error al buscar el libro: " + e.getMessage());
                    } catch (RuntimeException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 2 -> libroService.getAllLibros().forEach(libro -> System.out.println(libro.getTitulo()));
                case 3 -> autorService.getAllAutores().forEach(autor -> System.out.println(autor.getApellido() + ", " + autor.getNombre()));
                case 4 -> {
                    System.out.print("Ingrese el año: ");
                    int anio = scanner.nextInt();
                    autorService.getAutoresVivosEnAnio(anio).forEach(autor -> System.out.println(autor.getApellido() + ", " + autor.getNombre()));
                }
                case 5 -> {
                    System.out.print("Ingrese el idioma (e.g., EN, ES): ");
                    String idioma = scanner.nextLine();
                    libroService.getLibrosPorIdioma(idioma).forEach(libro -> System.out.println(libro.getTitulo()));
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }
}