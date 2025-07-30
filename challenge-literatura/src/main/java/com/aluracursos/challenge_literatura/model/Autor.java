package com.aluracursos.challenge_literatura.model;

import com.sun.jdi.LongValue;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

public class Autor {
    private Integer id;
    private String nombre;
    private Integer anio_cumpleanios;
    private Integer anio_muerte;

    public Autor() {}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anio_cumpleanios = Optional.ofNullable(datosAutor.anio_cumpleanios())
                .orElse(0L);
        this.anio_muerte = datosAutor.anio_muerte();
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
