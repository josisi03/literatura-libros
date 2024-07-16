package com.example.literatura.libros;


import com.example.literatura.libros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Libro findByTitulo(String titulo);
    List<Libro> findByDatos_Idioma(String idioma);
}
