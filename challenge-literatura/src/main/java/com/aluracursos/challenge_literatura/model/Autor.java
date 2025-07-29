package com.aluracursos.challenge_literatura.model;

public class Autor {
    private Integer id;
    private String nombre;
    private Integer anio_cumpleanios;
    private Integer anio_muerte;

    public Autor() {}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.anio_cumpleanios = datosAutor.anio_cumpleanios();
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
        return "Autor{" +
                "nombre='" + nombre + '\'' +
                ", anio_cumpleanios=" + anio_cumpleanios +
                ", anio_muerte=" + anio_muerte +
                '}';
    }
}
