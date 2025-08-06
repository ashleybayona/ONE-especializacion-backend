package com.aluracursos.challenge_literatura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "autor_id")
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

    public Long getId() {
        return id;
    }

    public Long getNumero_descargas() {
        return numero_descargas;
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

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
