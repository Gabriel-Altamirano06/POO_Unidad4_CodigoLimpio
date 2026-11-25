
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
import modelo.ConferenciaCientifica;
import modelo.ContenidoAudiovisual;
import modelo.Documental;
import modelo.Investigador;
import modelo.Pelicula;
import modelo.SerieDeTV;
import modelo.Temporada;
import modelo.VideoMusical;


// Implementa la lectura y escritura de los contenidos audiovisuales utilizando archivos CSV.

public class CsvContenidoRepository implements ContenidoRepository {

    private final Path carpetaDatos;

    private final Path archivoContenidos;
    private final Path archivoActores;
    private final Path archivoTemporadas;
    private final Path archivoInvestigadores;

    public CsvContenidoRepository() {
        this(Paths.get("data"));
    }

    public CsvContenidoRepository(Path carpetaDatos) {

        this.carpetaDatos = carpetaDatos;

        archivoContenidos = carpetaDatos.resolve("contenidos.csv");
        archivoActores = carpetaDatos.resolve("actores.csv");
        archivoTemporadas = carpetaDatos.resolve("temporadas.csv");
        archivoInvestigadores =
            carpetaDatos.resolve("investigadores.csv");
    }

  
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

            escribirFila(escritor,
                "tipo", "id", "titulo", "duracion", "genero",
                "dato1", "dato2", "dato3", "dato4"
            );

            for (ContenidoAudiovisual contenido : contenidos) {

                if (contenido instanceof Pelicula) {

                    Pelicula pelicula = (Pelicula) contenido;

                    escribirFila(escritor,
                        "PELICULA",
                        String.valueOf(contenido.getId()),
                        contenido.getTitulo(),
                        String.valueOf(
                            contenido.getDuracionEnMinutos()),
                        contenido.getGenero(),
                        pelicula.getEstudio(), "", "", ""
                    );

                } else if (contenido instanceof SerieDeTV) {

                    escribirFila(escritor,
                        "SERIE",
                        String.valueOf(contenido.getId()),
                        contenido.getTitulo(),
                        String.valueOf(
                            contenido.getDuracionEnMinutos()),
                        contenido.getGenero(),
                        "", "", "", ""
                    );

                } else if (contenido instanceof Documental) {

                    Documental documental = (Documental) contenido;

                    escribirFila(escritor,
                        "DOCUMENTAL",
                        String.valueOf(contenido.getId()),
                        contenido.getTitulo(),
                        String.valueOf(
                            contenido.getDuracionEnMinutos()),
                        contenido.getGenero(),
                        documental.getTema(), "", "", ""
                    );

                } else if (contenido instanceof VideoMusical) {

                    VideoMusical video = (VideoMusical) contenido;

                    escribirFila(escritor,
                        "VIDEO_MUSICAL",
                        String.valueOf(contenido.getId()),
                        contenido.getTitulo(),
                        String.valueOf(
                            contenido.getDuracionEnMinutos()),
                        contenido.getGenero(),
                        video.getArtistaPrincipal(),
                        video.getSelloDiscografico(),
                        String.valueOf(video.getAnioLanzamiento()),
                        ""
                    );

                } else if (contenido instanceof ConferenciaCientifica) {

                    ConferenciaCientifica conferencia =
                        (ConferenciaCientifica) contenido;

                    escribirFila(escritor,
                        "CONFERENCIA",
                        String.valueOf(contenido.getId()),
                        contenido.getTitulo(),
                        String.valueOf(
                            contenido.getDuracionEnMinutos()),
                        contenido.getGenero(),
                        conferencia.getTemaCentral(),
                        conferencia.getInstitucionOrganizadora(),
                        conferencia.getFechaRealizacion(),
                        conferencia.getModalidad()
                    );

                } else {

                    throw new IOException(
                        "Tipo de contenido no reconocido."
                    );
                }
            }
        }
    }

    private void guardarActores(
            List<ContenidoAudiovisual> contenidos)
            throws IOException {

        try (BufferedWriter escritor = Files.newBufferedWriter(
                archivoActores, StandardCharsets.UTF_8)) {

            escribirFila(escritor,
                "contenidoId", "tipoRelacion", "nombre",
                "nacionalidad", "edad"
            );

            for (ContenidoAudiovisual contenido : contenidos) {

                if (contenido instanceof Pelicula) {

                    Pelicula pelicula = (Pelicula) contenido;

                    for (Actor actor : pelicula.getActores()) {

                        escribirFila(escritor,
                            String.valueOf(contenido.getId()),
                            "PELICULA",
                            actor.getNombre(),
                            actor.getNacionalidad(),
                            String.valueOf(actor.getEdad())
                        );
                    }

                } else if (contenido instanceof VideoMusical) {

                    VideoMusical video = (VideoMusical) contenido;

                    for (Actor actor : video.getActoresParticipantes()) {

                        escribirFila(escritor,
                            String.valueOf(contenido.getId()),
                            "VIDEO_MUSICAL",
                            actor.getNombre(),
                            actor.getNacionalidad(),
                            String.valueOf(actor.getEdad())
                        );
                    }
                }
            }
        }
    }

    private void guardarTemporadas(
            List<ContenidoAudiovisual> contenidos)
            throws IOException {

        try (BufferedWriter escritor = Files.newBufferedWriter(
                archivoTemporadas, StandardCharsets.UTF_8)) {

            escribirFila(escritor,
                "contenidoId", "numeroTemporada",
                "anioEstreno", "numeroEpisodios"
            );

            for (ContenidoAudiovisual contenido : contenidos) {

                if (contenido instanceof SerieDeTV) {

                    SerieDeTV serie = (SerieDeTV) contenido;

                    for (Temporada temporada :
                            serie.getListaTemporadas()) {

                        escribirFila(escritor,
                            String.valueOf(contenido.getId()),
                            String.valueOf(
                                temporada.getNumeroTemporada()),
                            String.valueOf(
                                temporada.getAnioEstreno()),
                            String.valueOf(
                                temporada.getNumeroEpisodios())
                        );
                    }
                }
            }
        }
    }

    private void guardarInvestigadores(
            List<ContenidoAudiovisual> contenidos)
            throws IOException {

        try (BufferedWriter escritor = Files.newBufferedWriter(
                archivoInvestigadores, StandardCharsets.UTF_8)) {

            escribirFila(escritor,
                "contenidoId", "tipoRelacion", "nombre",
                "especialidad", "institucion"
            );

            for (ContenidoAudiovisual contenido : contenidos) {

                if (contenido instanceof Documental) {

                    Documental documental = (Documental) contenido;

                    for (Investigador investigador :
                            documental.getInvestigadores()) {

                        escribirFila(escritor,
                            String.valueOf(contenido.getId()),
                            "DOCUMENTAL",
                            investigador.getNombre(),
                            investigador.getEspecialidad(),
                            investigador.getInstitucion()
                        );
                    }

                } else if (contenido instanceof ConferenciaCientifica) {

                    ConferenciaCientifica conferencia =
                        (ConferenciaCientifica) contenido;

                    for (Investigador investigador :
                            conferencia.getInvestigadores()) {

                        escribirFila(escritor,
                            String.valueOf(contenido.getId()),
                            "CONFERENCIA",
                            investigador.getNombre(),
                            investigador.getEspecialidad(),
                            investigador.getInstitucion()
                        );
                    }
                }
            }
        }
    }


    @Override
    public List<ContenidoAudiovisual> cargar() throws IOException {

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

            ContenidoAudiovisual contenido;

            switch (tipo) {

                case "PELICULA":

                    contenido = new Pelicula(
                        titulo, duracion, genero, fila.get(5)
                    );
                    break;

                case "SERIE":

                    contenido = new SerieDeTV(
                        titulo, duracion, genero
                    );
                    break;

                case "DOCUMENTAL":

                    contenido = new Documental(
                        titulo, duracion, genero, fila.get(5)
                    );
                    break;

                case "VIDEO_MUSICAL":

                    contenido = new VideoMusical(
                        titulo,
                        duracion,
                        genero,
                        fila.get(5),
                        fila.get(6),
                        convertirEntero(fila.get(7),
                            "Año de lanzamiento")
                    );
                    break;

                case "CONFERENCIA":

                    contenido = new ConferenciaCientifica(
                        titulo,
                        duracion,
                        genero,
                        fila.get(5),
                        fila.get(6),
                        fila.get(7),
                        fila.get(8)
                    );
                    break;

                default:

                    throw new IOException(
                        "Tipo de contenido desconocido: " + tipo
                    );
            }

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

            String tipo = fila.get(1);

            Actor actor = new Actor(
                fila.get(2),
                fila.get(3),
                convertirEntero(fila.get(4), "Edad del actor")
            );

            ContenidoAudiovisual contenido =
                buscarContenido(contenidos, id);

            if ("PELICULA".equals(tipo)
                    && contenido instanceof Pelicula) {

                ((Pelicula) contenido).agregarActor(actor);

            } else if ("VIDEO_MUSICAL".equals(tipo)
                    && contenido instanceof VideoMusical) {

                ((VideoMusical) contenido)
                    .agregarActorParticipante(actor);

            } else {

                throw new IOException(
                    "Relacion de actor incorrecta para el ID " + id
                );
            }
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

            if (!(contenido instanceof SerieDeTV)) {

                throw new IOException(
                    "La temporada no pertenece a una serie. ID: " + id
                );
            }

            SerieDeTV serie = (SerieDeTV) contenido;

            int numero = convertirEntero(
                fila.get(1), "Numero de temporada"
            );

            int anio = convertirEntero(
                fila.get(2), "Año de estreno"
            );

            int episodios = convertirEntero(
                fila.get(3), "Numero de episodios"
            );

            serie.agregarTemporada(numero, anio, episodios);
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

            String tipo = fila.get(1);

            Investigador investigador = new Investigador(
                fila.get(2),
                fila.get(3),
                fila.get(4)
            );

            ContenidoAudiovisual contenido =
                buscarContenido(contenidos, id);

            if ("DOCUMENTAL".equals(tipo)
                    && contenido instanceof Documental) {

                ((Documental) contenido)
                    .agregarInvestigador(investigador);

            } else if ("CONFERENCIA".equals(tipo)
                    && contenido instanceof ConferenciaCientifica) {

                ((ConferenciaCientifica) contenido)
                    .agregarInvestigador(investigador);

            } else {

                throw new IOException(
                    "Relacion de investigador incorrecta. ID: " + id
                );
            }
        }
    }



// Escribe una fila CSV separando los campos por punto y coma.
// Las comillas internas se duplican para conservar los datos.
   
    private void escribirFila(
            BufferedWriter escritor, String... campos)
            throws IOException {

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


// Lee un archivo CSV y comprueba el numero de columnas.

    private List<List<String>> leerFilas(
            Path archivo, int columnasEsperadas)
            throws IOException {

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


// Interpreta los campos CSV respetando comillas y punto y coma.

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

    private int convertirEntero(String valor, String campo)
            throws IOException {

        try {
            return Integer.parseInt(valor.trim());

        } catch (NumberFormatException e) {

            throw new IOException(
                "Valor numerico incorrecto en el campo: " + campo,
                e
            );
        }
    }

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
