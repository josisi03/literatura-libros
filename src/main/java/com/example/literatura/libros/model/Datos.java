package com.example.literatura.libros.model;


import jakarta.persistence.Embeddable;

@Embeddable
public class Datos {
    private String idioma;
    private int descargas;

    public Datos() {}

    public Datos(String idioma, int descargas) {
        this.idioma = idioma;
        this.descargas = descargas;
    }

    // Getters y Setters

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getDescargas() {
        return descargas;
    }

    public void setDescargas(int descargas) {
        this.descargas = descargas;
    }
}
