
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import modelo.Actor;
import modelo.ContenidoAudiovisual;
import modelo.Investigador;
import modelo.Temporada;

import persistencia.adaptadores.ConferenciaCsvAdapter;
import persistencia.adaptadores.ContenidoCsvAdapter;
import persistencia.adaptadores.DocumentalCsvAdapter;
import persistencia.adaptadores.PeliculaCsvAdapter;
import persistencia.adaptadores.SerieCsvAdapter;
import persistencia.adaptadores.VideoMusicalCsvAdapter;

// Repositorio CSV que delega la conversion de cada tipo de contenido a un adaptador especializado.

public class CsvContenidoRepository implements ContenidoRepository {

    private final Path carpetaDatos;
    private final Path archivoContenidos;
    private final Path archivoActores;
    private final Path archivoTemporadas;
    private final Path archivoInvestigadores;

    private final List<ContenidoCsvAdapter<? extends ContenidoAudiovisual>>
        adaptadores;

    private final Map<String,
        ContenidoCsvAdapter<? extends ContenidoAudiovisual>>
        adaptadoresPorTipo;

    public CsvContenidoRepository() {
        this(Paths.get("data"));
    }

    public CsvContenidoRepository(Path carpetaDatos) {
        this(carpetaDatos, crearAdaptadoresPredeterminados());
    }


// Constructor que permite inyectar nuevos adaptadores.

    public CsvContenidoRepository(
            Path carpetaDatos,
            List<ContenidoCsvAdapter<? extends ContenidoAudiovisual>>
            adaptadores) {

        if (carpetaDatos == null || adaptadores == null
                || adaptadores.isEmpty()) {

            throw new IllegalArgumentException(
                "La carpeta y la lista de adaptadores son obligatorias."
            );
        }

        this.carpetaDatos = carpetaDatos;

        this.archivoContenidos =
            carpetaDatos.resolve("contenidos.csv");

        this.archivoActores =
            carpetaDatos.resolve("actores.csv");

        this.archivoTemporadas =
            carpetaDatos.resolve("temporadas.csv");

        this.archivoInvestigadores =
            carpetaDatos.resolve("investigadores.csv");

        this.adaptadores = new ArrayList<>(adaptadores);
        this.adaptadoresPorTipo = new LinkedHashMap<>();

        for (ContenidoCsvAdapter<? extends ContenidoAudiovisual> adaptador
                : this.adaptadores) {

            if (adaptador == null || adaptador.obtenerTipo() == null
                    || adaptador.obtenerTipo().trim().isEmpty()) {

                throw new IllegalArgumentException(
                    "Existe un adaptador invalido."
                );
            }

            if (adaptadoresPorTipo.containsKey(adaptador.obtenerTipo())) {

                throw new IllegalArgumentException(
                    "Tipo de contenido duplicado: "
                    + adaptador.obtenerTipo()
                );
            }

            adaptadoresPorTipo.put(
                adaptador.obtenerTipo(), adaptador
            );
        }
    }

    
// Registra los cinco adaptadores iniciales del sistema.

    private static List<ContenidoCsvAdapter<? extends ContenidoAudiovisual>>
            crearAdaptadoresPredeterminados() {

        List<ContenidoCsvAdapter<? extends ContenidoAudiovisual>> lista =
            new ArrayList<>();

        lista.add(new PeliculaCsvAdapter());
        lista.add(new SerieCsvAdapter());
        lista.add(new DocumentalCsvAdapter());
        lista.add(new VideoMusicalCsvAdapter());
        lista.add(new ConferenciaCsvAdapter());

        return lista;
    }


    // GUARDAR INFORMACION
  

    @Override
    public void guardar(List<ContenidoAudiovisual> contenidos)
            throws IOException {

        if (contenidos == null) {

            throw new IllegalArgumentException(
                "La lista de contenidos no puede ser nula."
            );
        }

        Files.createDirectories(carpetaDatos);

        guardarContenidos(contenidos);
        guardarActores(contenidos);
        guardarTemporadas(contenidos);
        guardarInvestigadores(contenidos);
    }

    private void guardarContenidos(
            List<ContenidoAudiovisual> contenidos)
            throws IOException {

        try (BufferedWriter escritor = Files.newBufferedWriter(
                archivoContenidos, StandardCharsets.UTF_8)) {

            escribirFila(
                escritor,
                "tipo", "id", "titulo", "duracion",
                "genero", "dato1", "dato2", "dato3", "dato4"
            );

            for (ContenidoAudiovisual contenido : contenidos) {

                ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                    adaptador = buscarAdaptador(contenido);

                String[] datos =
                    obtenerDatosEspecificos(adaptador, contenido);

                escribirFila(
                    escritor,
                    adaptador.obtenerTipo(),
                    String.valueOf(contenido.getId()),
                    contenido.getTitulo(),
                    String.valueOf(
                        contenido.getDuracionEnMinutos()
                    ),
                    contenido.getGenero(),
                    datos[0],
                    datos[1],
                    datos[2],
                    datos[3]
                );
            }
        }
    }

    private void guardarActores(
            List<ContenidoAudiovisual> contenidos)
            throws IOException {

        try (BufferedWriter escritor = Files.newBufferedWriter(
                archivoActores, StandardCharsets.UTF_8)) {

            escribirFila(
                escritor,
                "contenidoId", "tipoRelacion", "nombre",
                "nacionalidad", "edad"
            );

            for (ContenidoAudiovisual contenido : contenidos) {

                ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                    adaptador = buscarAdaptador(contenido);

                for (String[] fila :
                        obtenerFilasActores(adaptador, contenido)) {

                    escribirFila(escritor, fila);
                }
            }
        }
    }

    private void guardarTemporadas(
            List<ContenidoAudiovisual> contenidos)
            throws IOException {

        try (BufferedWriter escritor = Files.newBufferedWriter(
                archivoTemporadas, StandardCharsets.UTF_8)) {

            escribirFila(
                escritor,
                "contenidoId", "numeroTemporada",
                "anioEstreno", "numeroEpisodios"
            );

            for (ContenidoAudiovisual contenido : contenidos) {

                ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                    adaptador = buscarAdaptador(contenido);

                for (String[] fila :
                        obtenerFilasTemporadas(adaptador, contenido)) {

                    escribirFila(escritor, fila);
                }
            }
        }
    }

    private void guardarInvestigadores(
            List<ContenidoAudiovisual> contenidos)
            throws IOException {

        try (BufferedWriter escritor = Files.newBufferedWriter(
                archivoInvestigadores, StandardCharsets.UTF_8)) {

            escribirFila(
                escritor,
                "contenidoId", "tipoRelacion", "nombre",
                "especialidad", "institucion"
            );

            for (ContenidoAudiovisual contenido : contenidos) {

                ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                    adaptador = buscarAdaptador(contenido);

                for (String[] fila :
                        obtenerFilasInvestigadores(adaptador, contenido)) {

                    escribirFila(escritor, fila);
                }
            }
        }
    }


    // CARGAR INFORMACION


    @Override
    public List<ContenidoAudiovisual> cargar()
            throws IOException {

        Map<Integer, ContenidoAudiovisual> contenidos =
            new LinkedHashMap<>();

        cargarContenidos(contenidos);
        cargarActores(contenidos);
        cargarTemporadas(contenidos);
        cargarInvestigadores(contenidos);

        return new ArrayList<>(contenidos.values());
    }

    private void cargarContenidos(
            Map<Integer, ContenidoAudiovisual> contenidos)
            throws IOException {

        List<List<String>> filas =
            leerFilas(archivoContenidos, 9);

        for (List<String> fila : filas) {

            String tipo = fila.get(0);

            int id = convertirEntero(
                fila.get(1), "ID del contenido"
            );

            String titulo = fila.get(2);

            int duracion = convertirEntero(
                fila.get(3), "Duracion"
            );

            String genero = fila.get(4);

            String[] datos = {
                fila.get(5),
                fila.get(6),
                fila.get(7),
                fila.get(8)
            };

            ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                adaptador = adaptadoresPorTipo.get(tipo);

            if (adaptador == null) {

                throw new IOException(
                    "Tipo de contenido desconocido: " + tipo
                );
            }

            ContenidoAudiovisual contenido = crearContenido(
                adaptador, titulo, duracion, genero, datos
            );

            contenido.restaurarIdentificador(id);

            if (contenidos.containsKey(id)) {

                throw new IOException(
                    "Identificador duplicado: " + id
                );
            }

            contenidos.put(id, contenido);
        }
    }

    private void cargarActores(
            Map<Integer, ContenidoAudiovisual> contenidos)
            throws IOException {

        List<List<String>> filas =
            leerFilas(archivoActores, 5);

        for (List<String> fila : filas) {

            int id = convertirEntero(
                fila.get(0), "ID de contenido"
            );

            String tipoRelacion = fila.get(1);

            Actor actor = new Actor(
                fila.get(2),
                fila.get(3),
                convertirEntero(fila.get(4), "Edad del actor")
            );

            ContenidoAudiovisual contenido =
                buscarContenido(contenidos, id);

            ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                adaptador = buscarAdaptador(contenido);

            agregarActor(
                adaptador, contenido, tipoRelacion, actor
            );
        }
    }

    private void cargarTemporadas(
            Map<Integer, ContenidoAudiovisual> contenidos)
            throws IOException {

        List<List<String>> filas =
            leerFilas(archivoTemporadas, 4);

        for (List<String> fila : filas) {

            int id = convertirEntero(
                fila.get(0), "ID de contenido"
            );

            ContenidoAudiovisual contenido =
                buscarContenido(contenidos, id);

            ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                adaptador = buscarAdaptador(contenido);

            Temporada temporada = new Temporada(
                convertirEntero(
                    fila.get(1), "Numero de temporada"
                ),
                convertirEntero(
                    fila.get(2), "Anio de estreno"
                ),
                convertirEntero(
                    fila.get(3), "Numero de episodios"
                )
            );

            agregarTemporada(
                adaptador, contenido, temporada
            );
        }
    }

    private void cargarInvestigadores(
            Map<Integer, ContenidoAudiovisual> contenidos)
            throws IOException {

        List<List<String>> filas =
            leerFilas(archivoInvestigadores, 5);

        for (List<String> fila : filas) {

            int id = convertirEntero(
                fila.get(0), "ID de contenido"
            );

            String tipoRelacion = fila.get(1);

            Investigador investigador = new Investigador(
                fila.get(2),
                fila.get(3),
                fila.get(4)
            );

            ContenidoAudiovisual contenido =
                buscarContenido(contenidos, id);

            ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                adaptador = buscarAdaptador(contenido);

            agregarInvestigador(
                adaptador, contenido, tipoRelacion, investigador
            );
        }
    }


    // SELECCION DE ADAPTADORES


    private ContenidoCsvAdapter<? extends ContenidoAudiovisual>
            buscarAdaptador(ContenidoAudiovisual contenido)
            throws IOException {

        if (contenido == null) {

            throw new IOException(
                "No se puede buscar un adaptador para contenido nulo."
            );
        }

        for (ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                adaptador : adaptadores) {

            if (adaptador.obtenerClase().isInstance(contenido)) {
                return adaptador;
            }
        }

        throw new IOException(
            "No existe un adaptador registrado para: "
            + contenido.getClass().getSimpleName()
        );
    }
    
   // METODOS AUXILIARES PARA GENERICOS


    private String[] obtenerDatosEspecificos(
            ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                adaptador,
            ContenidoAudiovisual contenido) {

        return obtenerDatosTipados(adaptador, contenido);
    }

    private <T extends ContenidoAudiovisual> String[]
            obtenerDatosTipados(
                ContenidoCsvAdapter<T> adaptador,
                ContenidoAudiovisual contenido) {

        return adaptador.obtenerDatosEspecificos(
            adaptador.obtenerClase().cast(contenido)
        );
    }

    private ContenidoAudiovisual crearContenido(
            ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                adaptador,
            String titulo,
            int duracion,
            String genero,
            String[] datos) throws IOException {

        return crearContenidoTipado(
            adaptador, titulo, duracion, genero, datos
        );
    }

    private <T extends ContenidoAudiovisual> T crearContenidoTipado(
            ContenidoCsvAdapter<T> adaptador,
            String titulo,
            int duracion,
            String genero,
            String[] datos) throws IOException {

        return adaptador.crearContenido(
            titulo, duracion, genero, datos
        );
    }

    private List<String[]> obtenerFilasActores(
            ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                adaptador,
            ContenidoAudiovisual contenido) {

        return obtenerFilasActoresTipadas(adaptador, contenido);
    }

    private <T extends ContenidoAudiovisual>
            List<String[]> obtenerFilasActoresTipadas(
                ContenidoCsvAdapter<T> adaptador,
                ContenidoAudiovisual contenido) {

        return adaptador.obtenerFilasActores(
            adaptador.obtenerClase().cast(contenido),
            contenido.getId()
        );
    }

    private List<String[]> obtenerFilasTemporadas(
            ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                adaptador,
            ContenidoAudiovisual contenido) {

        return obtenerFilasTemporadasTipadas(adaptador, contenido);
    }

    private <T extends ContenidoAudiovisual>
            List<String[]> obtenerFilasTemporadasTipadas(
                ContenidoCsvAdapter<T> adaptador,
                ContenidoAudiovisual contenido) {

        return adaptador.obtenerFilasTemporadas(
            adaptador.obtenerClase().cast(contenido),
            contenido.getId()
        );
    }

    private List<String[]> obtenerFilasInvestigadores(
            ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                adaptador,
            ContenidoAudiovisual contenido) {

        return obtenerFilasInvestigadoresTipadas(
            adaptador, contenido
        );
    }

    private <T extends ContenidoAudiovisual>
            List<String[]> obtenerFilasInvestigadoresTipadas(
                ContenidoCsvAdapter<T> adaptador,
                ContenidoAudiovisual contenido) {

        return adaptador.obtenerFilasInvestigadores(
            adaptador.obtenerClase().cast(contenido),
            contenido.getId()
        );
    }

    private void agregarActor(
            ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                adaptador,
            ContenidoAudiovisual contenido,
            String tipoRelacion,
            Actor actor) throws IOException {

        agregarActorTipado(
            adaptador, contenido, tipoRelacion, actor
        );
    }

    private <T extends ContenidoAudiovisual> void agregarActorTipado(
            ContenidoCsvAdapter<T> adaptador,
            ContenidoAudiovisual contenido,
            String tipoRelacion,
            Actor actor) throws IOException {

        adaptador.agregarActor(
            adaptador.obtenerClase().cast(contenido),
            tipoRelacion,
            actor
        );
    }

    private void agregarTemporada(
            ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                adaptador,
            ContenidoAudiovisual contenido,
            Temporada temporada) throws IOException {

        agregarTemporadaTipado(
            adaptador, contenido, temporada
        );
    }

    private <T extends ContenidoAudiovisual>
            void agregarTemporadaTipado(
                ContenidoCsvAdapter<T> adaptador,
                ContenidoAudiovisual contenido,
                Temporada temporada) throws IOException {

        adaptador.agregarTemporada(
            adaptador.obtenerClase().cast(contenido),
            temporada
        );
    }

    private void agregarInvestigador(
            ContenidoCsvAdapter<? extends ContenidoAudiovisual>
                adaptador,
            ContenidoAudiovisual contenido,
            String tipoRelacion,
            Investigador investigador) throws IOException {

        agregarInvestigadorTipado(
            adaptador, contenido, tipoRelacion, investigador
        );
    }

    private <T extends ContenidoAudiovisual>
            void agregarInvestigadorTipado(
                ContenidoCsvAdapter<T> adaptador,
                ContenidoAudiovisual contenido,
                String tipoRelacion,
                Investigador investigador) throws IOException {

        adaptador.agregarInvestigador(
            adaptador.obtenerClase().cast(contenido),
            tipoRelacion,
            investigador
        );
    }


    // ESCRITURA Y LECTURA CSV
   

// Escribe una fila CSV respetando comillas y separadores.

    private void escribirFila(
            BufferedWriter escritor,
            String... campos) throws IOException {

        for (int i = 0; i < campos.length; i++) {

            if (i > 0) {
                escritor.write(';');
            }

            String valor = campos[i] == null ? "" : campos[i];

            escritor.write('"');

            for (int j = 0; j < valor.length(); j++) {

                char caracter = valor.charAt(j);

                if (caracter == '"') {
                    escritor.write("\"\"");
                } else {
                    escritor.write(caracter);
                }
            }

            escritor.write('"');
        }

        escritor.newLine();
    }
    
 // Lee las filas de un archivo CSV y valida sus columnas.
   
    private List<List<String>> leerFilas(
            Path archivo,
            int columnasEsperadas) throws IOException {

        List<List<String>> filas = new ArrayList<>();

        if (!Files.exists(archivo)) {
            return filas;
        }

        try (BufferedReader lector = Files.newBufferedReader(
                archivo, StandardCharsets.UTF_8)) {

            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {

                numeroLinea++;

                if (numeroLinea == 1 || linea.trim().isEmpty()) {
                    continue;
                }

                List<String> campos;

                try {

                    campos = parsearLinea(linea);

                } catch (IllegalArgumentException e) {

                    throw new IOException(
                        "Error de formato en "
                        + archivo.getFileName()
                        + ", linea " + numeroLinea,
                        e
                    );
                }

                if (campos.size() != columnasEsperadas) {

                    throw new IOException(
                        "Numero incorrecto de columnas en "
                        + archivo.getFileName()
                        + ", linea " + numeroLinea
                    );
                }

                filas.add(campos);
            }
        }

        return filas;
    }

 
// Interpreta una linea CSV respetando comillas.

    private List<String> parsearLinea(String linea) {

        List<String> campos = new ArrayList<>();

        StringBuilder campo = new StringBuilder();

        boolean dentroComillas = false;

        for (int i = 0; i < linea.length(); i++) {

            char caracter = linea.charAt(i);

            if (caracter == '"') {

                if (dentroComillas
                        && i + 1 < linea.length()
                        && linea.charAt(i + 1) == '"') {

                    campo.append('"');
                    i++;

                } else {

                    dentroComillas = !dentroComillas;
                }

            } else if (caracter == ';' && !dentroComillas) {

                campos.add(campo.toString());
                campo.setLength(0);

            } else {

                campo.append(caracter);
            }
        }

        if (dentroComillas) {

            throw new IllegalArgumentException(
                "Campo CSV con comillas incompletas."
            );
        }

        campos.add(campo.toString());

        return campos;
    }

   
// Convierte un valor de texto en numero entero.

    private int convertirEntero(
            String valor, String campo) throws IOException {

        try {

            return Integer.parseInt(valor.trim());

        } catch (NumberFormatException e) {

            throw new IOException(
                "Valor numerico incorrecto en el campo: " + campo,
                e
            );
        }
    }

    
// Busca un contenido mediante su identificador.
 
    private ContenidoAudiovisual buscarContenido(
            Map<Integer, ContenidoAudiovisual> contenidos,
            int id) throws IOException {

        ContenidoAudiovisual contenido = contenidos.get(id);

        if (contenido == null) {

            throw new IOException(
                "No existe el contenido relacionado con el ID: " + id
            );
        }

        return contenido;
    }
}
