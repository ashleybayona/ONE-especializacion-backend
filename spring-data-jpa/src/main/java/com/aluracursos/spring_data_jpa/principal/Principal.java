package com.aluracursos.spring_data_jpa.principal;

import com.aluracursos.spring_data_jpa.model.*;
import com.aluracursos.spring_data_jpa.repository.SerieRepository;
import com.aluracursos.spring_data_jpa.service.ConsumoAPI;
import com.aluracursos.spring_data_jpa.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6d485162";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private List<Serie> series;
    private Optional<Serie> serieEncontrada;

    public Principal(SerieRepository repository) {
        this.repositorio=repository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar serie por titulo
                    5 - Ver top 5
                    6 - Buscar series por categoria
                    7 - Buscar por num temporadas y evaluacion
                    8 - Buscar episodio
                    9 - Top 5 episodios por serie
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    mostrarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    mostrarTop5();
                    break;
                case 6:
                    buscarPorCategoria();
                    break;
                case 7:
                    buscarNumTemporadasEvaluacion();
                    break;
                case 8:
                    buscarEpisodiosPorNombre();
                    break;
                case 9:
                    buscarTop5Serie();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void buscarTop5Serie() {
        buscarSeriePorTitulo();
        if(serieEncontrada.isPresent()){
            Serie serie = serieEncontrada.get();
            List<Episodio> topEpisodios = repositorio.top5EpPorSerie(serie);
            topEpisodios.forEach(e -> System.out.printf("Serie: %s - Temporada: %s - Episodio: %s - Evaluacion: %s\n",
                    e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));
        }
    }

    private void buscarEpisodiosPorNombre() {
        System.out.println("Escribe nombre del ep que deseas buscar: ");
        var nomEp = teclado.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorNombre(nomEp);
        episodiosEncontrados.forEach(e -> System.out.printf("Serie: %s - Temporada: %s - Episodio: %s - Evaluacion: %s\n",
                e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion()));
    }

    private void buscarNumTemporadasEvaluacion() {
        System.out.println("Ingrese el número máximo de temporadas: ");
        var numTemporadas = teclado.nextInt(); teclado.nextLine();
        System.out.println("Ingrese la evaluacion minima: ");
        var evMinima = teclado.nextDouble(); teclado.nextLine();

        List<Serie> seriesEncontradas = repositorio.filtrarPorTemporadaYEvaluacion(numTemporadas, evMinima);

        if(!seriesEncontradas.isEmpty()) {
            seriesEncontradas.forEach(s -> System.out.println("Serie: "+s.getTitulo()));
        } else {
            System.out.println("No se encontraron series");
        }
    }

    private void buscarPorCategoria() {
        System.out.println("Ingrese el genero a buscar: ");
        var categoriaBuscar = Categoria.fromEspanol(teclado.nextLine());
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoriaBuscar);
        System.out.println("Series de la categoria "+categoriaBuscar+":");
        seriesPorCategoria.forEach(s -> System.out.println(s.getTitulo()));
    }

    private void mostrarTop5() {
        List<Serie> top5 = repositorio.findTop5ByOrderByEvaluacionDesc();
        top5.forEach(s -> System.out.println("Serie: "+s.getTitulo()+" - Evaluacion: "+s.getEvaluacion()));
    }

    private void buscarSeriePorTitulo() {
        System.out.print("Ingrese el titulo de la serie: ");
        var nombreBuscar = teclado.nextLine();
        serieEncontrada = repositorio.findByTituloContainsIgnoreCase(nombreBuscar);
        if (serieEncontrada.isPresent()) {
            System.out.println("La serie encontrada es: " + serieEncontrada.get());
        } else {
            System.out.println("La serie no existe");
        }
    }

    private DatosSerie getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }
    private void buscarEpisodioPorSerie() {
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie de la cual quieres ver los episodios: ");
        var nombreSerie = teclado.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                .findFirst();

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DatosTemporadas> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }


    }
    private void buscarSerieWeb() {
        DatosSerie datos = getDatosSerie();
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        //datosSeries.add(datos);
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {
        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
}
