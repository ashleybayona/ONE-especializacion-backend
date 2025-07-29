package com.aluracursos.challenge_literatura;

import com.aluracursos.challenge_literatura.model.DatosAPI;
import com.aluracursos.challenge_literatura.principal.Principal;
import com.aluracursos.challenge_literatura.service.ConsumoAPI;
import com.aluracursos.challenge_literatura.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ChallengeLiteraturaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		ConsumoAPI consumoAPI = new ConsumoAPI();
		String json = consumoAPI.obtenerDatos(principal.URL);
		ConvierteDatos conversor = new ConvierteDatos();
		DatosAPI data = conversor.obtenerDatos(json, DatosAPI.class);
		System.out.println(data);
	}
}
