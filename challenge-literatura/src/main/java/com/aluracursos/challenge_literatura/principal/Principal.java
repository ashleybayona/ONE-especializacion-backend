package com.aluracursos.challenge_literatura.principal;

import com.aluracursos.challenge_literatura.model.Autor;
import com.aluracursos.challenge_literatura.model.DatosAPI;
import com.aluracursos.challenge_literatura.model.DatosLibro;
import com.aluracursos.challenge_literatura.model.Libro;
import com.aluracursos.challenge_literatura.service.ConsumoAPI;
import com.aluracursos.challenge_literatura.service.ConvierteDatos;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    public final String URL_BASE = "http://gutendex.com/books/?";
    private ConvierteDatos conversor = new ConvierteDatos();
    private Set<Libro> librosRegistrados = new HashSet<>();
    private Set<Autor> autoresRegistrados = new HashSet<>();

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
            System.out.print(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosEnAnio();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 0);
    }

    private DatosLibro getDatosLibro() {
        DatosLibro data = null;
        System.out.println("\nIngrese el nombre del libro: ");
        String nombreLibro = scanner.nextLine();
        var json =consumoAPI.obtenerDatos(URL_BASE+"search="+nombreLibro.replace(" ", "%20"));
        var results = conversor.obtenerDatos(json, DatosAPI.class);
        data = results.libros().getFirst();
        return data;
    }

    private List<Libro> getLibrosPorIdioma() {
        System.out.print("""
                \nIngrese abreviatura del idioma a buscar:
                1. es - Español
                2. en - Inglés
                3. fr - Francés
                4. pt - Portugués
                -> Ingrese opción: 
                """);
        var filtroIdioma = scanner.nextInt();
        scanner.nextLine();
        List<Libro> librosPorIdioma = librosRegistrados.stream()
                .filter(libro -> libro.getLenguaje().equals(filtroIdioma))
                .collect(Collectors.toList());
        return librosPorIdioma;
    }

    private List<Autor> getAutoresPorAnio() {
        System.out.print("\nIngrese el año: ");
        var filtroAnio = scanner.nextInt();
        scanner.nextLine();
        List<Autor> autoresPorAnio = autoresRegistrados.stream()
                .filter(autor -> autor.getAnio_muerte() < filtroAnio)
                .collect(Collectors.toList());
        return autoresPorAnio;
    }

    private void buscarLibro() {
        DatosLibro datosLibro = getDatosLibro();
        if (datosLibro != null) {
            Libro libro = new Libro(datosLibro);
            Autor autor = libro.getAutor();
            System.out.println("\nLibro encontrado:");
            System.out.println(libro);
            librosRegistrados.add(libro);
            autoresRegistrados.add(autor);
        } else {
            System.out.println("\nLibro no encontrado");
        }
    }

    private void mostrarLibrosRegistrados() {
        librosRegistrados.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void mostrarAutoresRegistrados() {
        autoresRegistrados.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void mostrarAutoresVivosEnAnio() {
        List<Autor> autoresVivosPorAnio = getAutoresPorAnio();
        autoresVivosPorAnio.forEach(System.out::println);
    }

    private void mostrarLibrosPorIdioma() {
        List<Libro> librosPorIdioma = getLibrosPorIdioma();
        librosPorIdioma.forEach(System.out::println);
    }
}
