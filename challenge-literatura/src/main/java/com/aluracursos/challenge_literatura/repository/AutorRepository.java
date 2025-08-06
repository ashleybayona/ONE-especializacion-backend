package com.aluracursos.challenge_literatura.repository;

import com.aluracursos.challenge_literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}
