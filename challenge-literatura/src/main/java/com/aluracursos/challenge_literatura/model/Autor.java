package com.aluracursos.challenge_literatura.model;

import com.sun.jdi.LongValue;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

@Entity
@Table(name="autores")

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer anio_cumpleanios;
    private Integer anio_muerte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anio_cumpleanios = Optional.ofNullable(datosAutor.anio_cumpleanios())
                .orElse(0);
        this.anio_muerte = Optional.ofNullable(datosAutor.anio_muerte())
                .orElse(0);
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getAnio_cumpleanios() {
        return anio_cumpleanios;
    }

    public Integer getAnio_muerte() {
        return anio_muerte;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(l -> l.setAutor(this));
        this.libros = libros;
    }

    @Override
    public String toString() {
        return String.format("""
                ---------------------------------------
                                AUTOR
                ---------------------------------------
                Nombre: %s
                Año de nacimiento: %s
                Año de muerte: %s
                """, nombre, anio_cumpleanios, anio_muerte);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return nombre.equals(autor.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
