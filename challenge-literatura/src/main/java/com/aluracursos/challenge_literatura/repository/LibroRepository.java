package com.aluracursos.challenge_literatura.repository;

import com.aluracursos.challenge_literatura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
