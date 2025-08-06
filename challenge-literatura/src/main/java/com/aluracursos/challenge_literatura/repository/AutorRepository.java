package com.aluracursos.challenge_literatura.repository;

import com.aluracursos.challenge_literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.anio_muerte != 0 AND a.anio_muerte > :anio")
    Optional<List<Autor>> getAutoresVivosEn(Integer anio);

    Optional<Autor> findByNombreIgnoreCase(String nombre);

    default Autor findOrCreateByNombre(Autor autor) {
        return findByNombreIgnoreCase(autor.getNombre())
                .orElseGet(() -> save(autor));
    }
}
