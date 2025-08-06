package com.aluracursos.challenge_literatura.principal;

import com.aluracursos.challenge_literatura.model.Autor;
import com.aluracursos.challenge_literatura.model.DatosAPI;
import com.aluracursos.challenge_literatura.model.DatosLibro;
import com.aluracursos.challenge_literatura.model.Libro;
import com.aluracursos.challenge_literatura.repository.AutorRepository;
import com.aluracursos.challenge_literatura.repository.LibroRepository;
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
    private LibroRepository librosRepository;
    private AutorRepository autoresRepository;
    private  List<Libro> libros;
    private  List<Autor> autores;

    public Principal() {}

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.librosRepository = libroRepository;
        this.autoresRepository = autorRepository;

    }

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
        System.out.println("\nIngrese el nombre del libro: ");
        String nombreLibro = scanner.nextLine();
        var json =consumoAPI.obtenerDatos(URL_BASE+"search="+nombreLibro.replace(" ", "%20"));
        var results = conversor.obtenerDatos(json, DatosAPI.class);
        DatosLibro data = results.libros().getFirst();
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
        var filtroIdioma = scanner.nextLine();

        List<Libro> todosLosLibros = librosRepository.findAll();

        List<Libro> librosPorIdioma = todosLosLibros.stream()
                .filter(l -> l.getLenguaje().equals(filtroIdioma))
                .collect(Collectors.toList());
        return librosPorIdioma;
    }

    private List<Autor> getAutoresPorAnio() {
        System.out.print("\nIngrese el año: ");
        var filtroAnio = scanner.nextInt();
        scanner.nextLine();

        List<Autor> todosLosAutores = autoresRepository.findAll();

        List<Autor> autoresPorAnio = todosLosAutores.stream()
                .filter(a -> a.getAnio_muerte() != 0 && a.getAnio_muerte() > filtroAnio)
                .collect(Collectors.toList());
        return autoresPorAnio;
    }

    // LISTO
    private void buscarLibro() {
        DatosLibro datosLibro = getDatosLibro();
        if (datosLibro != null) {
            Libro libro = new Libro(datosLibro);
            Autor autor = libro.getAutor();
            System.out.println("\nLibro encontrado:");
            System.out.println(libro);
            //librosRegistrados.add(libro);
            //autoresRegistrados.add(autor);
            librosRepository.save(libro);
        } else {
            System.out.println("\nLibro no encontrado");
        }
    }

    // LIBROS
    private void mostrarLibrosRegistrados() {
        libros = librosRepository.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    // LISTO
    private void mostrarAutoresRegistrados() {
        autores = autoresRepository.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    // LISTO
    private void mostrarAutoresVivosEnAnio() {
        Optional<List<Autor>> autoresVivosPorAnio = Optional.ofNullable(getAutoresPorAnio());
        if (autoresVivosPorAnio.isPresent()) {
            autoresVivosPorAnio.get().forEach(System.out::println);
        } else {
            System.out.println("\nNo se encontraron autores vivos en el año mencionado.");
        }
    }

    // LISTO
    private void mostrarLibrosPorIdioma() {
        Optional<List<Libro>> librosPorIdioma = Optional.ofNullable(getLibrosPorIdioma());
        if (librosPorIdioma.isPresent()) {
            librosPorIdioma.get().forEach(System.out::println);
        } else {
            System.out.println("\nNo se encontraron libros en el idioma seleccionado.");
        }
    }
}
