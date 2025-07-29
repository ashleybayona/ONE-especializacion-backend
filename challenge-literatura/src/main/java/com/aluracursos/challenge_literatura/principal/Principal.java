package com.aluracursos.challenge_literatura.principal;

import com.aluracursos.challenge_literatura.service.ConsumoAPI;
import com.aluracursos.challenge_literatura.service.ConvierteDatos;

import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    public final String URL = "http://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();

    public Principal() {}

    public void mostrarMenu() {
        int opcion;
        do {
            var menu = """
                    ------------------------------------------------
                                           MENU
                    ------------------------------------------------
                    1. Buscar libro por título
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en un determinado año
                    5. Listar libros por idiomas
                    0. Salir  
                    -> Ingrese opción:                
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    //buscarLibro();
                    break;
                case 2:
                    //mostrarLibrosRegistrados();
                    break;
                case 3:
                    //mostrarAutoresRegistrados();
                    break;
                case 4:
                    //mostrarAutoresVivosEnAnio();
                    break;
                case 5:
                    //mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 0);
    }
}
