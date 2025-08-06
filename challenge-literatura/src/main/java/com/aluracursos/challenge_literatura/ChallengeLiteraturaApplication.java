package com.aluracursos.challenge_literatura;

import com.aluracursos.challenge_literatura.model.DatosAPI;
import com.aluracursos.challenge_literatura.model.DatosLibro;
import com.aluracursos.challenge_literatura.model.Libro;
import com.aluracursos.challenge_literatura.principal.Principal;
import com.aluracursos.challenge_literatura.repository.AutorRepository;
import com.aluracursos.challenge_literatura.repository.LibroRepository;
import com.aluracursos.challenge_literatura.service.ConsumoAPI;
import com.aluracursos.challenge_literatura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class ChallengeLiteraturaApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libros;
	@Autowired
	private AutorRepository autores;
	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libros, autores);
		principal.mostrarMenu();
	}
}
