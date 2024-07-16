package com.example.literatura.libros;

import com.example.literatura.libros.model.Autor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> getAllAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> getAutoresVivosEnAnio(int anio) {
        return autorRepository.findByAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(anio, anio);
    }

    public Autor saveAutor(Autor autor) {
        return autorRepository.save(autor);
    }
}
