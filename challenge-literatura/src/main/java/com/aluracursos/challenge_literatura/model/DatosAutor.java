package com.aluracursos.challenge_literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer anio_cumpleanios,
        @JsonAlias("death_year") Integer anio_muerte
) {
}
