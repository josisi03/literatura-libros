package com.example.literatura.libros;

import com.example.literatura.libros.model.Autor;
import com.example.literatura.libros.model.Libro;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
    }

    public List<Libro> getLibrosPorIdioma(String idioma) {
        return libroRepository.findByDatos_Idioma(idioma);
    }

    @Transactional
    public Libro saveLibro(Libro libro) {
        Autor autor = libro.getAutor();
        if (autor.getId() == null) {
            autor = autorRepository.save(autor);
            libro.setAutor(autor);
        }
        if (libroRepository.findByTitulo(libro.getTitulo()) == null) {
            return libroRepository.save(libro);
        } else {
            throw new IllegalArgumentException("El libro ya existe en la base de datos.");
        }
    }
}
