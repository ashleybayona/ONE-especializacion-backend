package com.aluracursos.challenge_literatura.model;

import java.util.List;
import java.util.Objects;

public class Libro {
    private Integer id;
    private String titulo;
    private Autor autor;
    private String lenguaje;
    private Long numero_descargas;

    public Libro (){}

    public Libro (DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autor().getFirst());
        this.lenguaje = datosLibro.lenguaje().getFirst();
        this.numero_descargas = datosLibro.numero_descargas();
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
        return String.format("""
                ---------------------------------------
                                LIBRO
                ---------------------------------------
                Título: %s
                Autor: %s
                Lenguaje: %s
                Numero de descargas: %s
                """, titulo, autor.getNombre(), lenguaje, numero_descargas);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return titulo.equals(libro.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo);
    }
}
