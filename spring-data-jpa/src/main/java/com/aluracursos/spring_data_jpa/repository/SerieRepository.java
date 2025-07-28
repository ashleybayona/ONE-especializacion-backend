package com.aluracursos.spring_data_jpa.repository;

import com.aluracursos.spring_data_jpa.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
