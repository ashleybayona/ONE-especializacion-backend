package com.aluracursos.spring_data_jpa.repository;

import com.aluracursos.spring_data_jpa.model.Categoria;
import com.aluracursos.spring_data_jpa.model.Episodio;
import com.aluracursos.spring_data_jpa.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainsIgnoreCase(String titulo);
    List<Serie> findTop5ByOrderByEvaluacionDesc();
    List<Serie> findByGenero(Categoria categoria);
    //Optional<List<Serie>> findByTotalTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(Integer temporadas, Double evaluacion);

    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemp AND s.evaluacion >= :ev")
    List<Serie> filtrarPorTemporadaYEvaluacion(Integer totalTemp, Double ev);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombre%")
    List<Episodio> episodiosPorNombre(String nombre);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5EpPorSerie(Serie serie);
}
