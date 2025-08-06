package com.aluracursos.challenge_literatura.repository;

import com.aluracursos.challenge_literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<List<Libro>> findByLenguajeIsLikeIgnoreCase(String lenguaje);
}
