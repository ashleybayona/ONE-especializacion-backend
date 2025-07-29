package com.aluracursos.challenge_literatura.model;

import java.util.List;

public class Libro {
    private Integer id;
    private String titulo;
    private Autor autor;
    private String lenguaje;

    public Libro (){}

    public Libro (DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autor().getFirst());
        this.lenguaje = datosLibro.lenguaje().getFirst();
    }

    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", lenguaje='" + lenguaje + '\'' +
                '}';
    }
}
