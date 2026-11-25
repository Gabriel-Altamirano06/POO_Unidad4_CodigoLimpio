
package persistencia;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import modelo.Actor;
import modelo.ConferenciaCientifica;
import modelo.ContenidoAudiovisual;
import modelo.Documental;
import modelo.Investigador;
import modelo.Pelicula;
import modelo.SerieDeTV;
import modelo.VideoMusical;


// Prueba la lectura y escritura de los archivos CSV.

public class CsvContenidoRepositoryTest {

    @Rule
    public TemporaryFolder carpetaTemporal = new TemporaryFolder();

   
// Comprueba el guardado y recuperacion de los cinco tipos de contenidos y sus elementos relacionados.
  
    @Test
    public void guardarYCargarTodosLosTiposDeContenido()
            throws Exception {

        Path carpeta = carpetaTemporal
            .newFolder("datosCompletos").toPath();

        CsvContenidoRepository repositorio =
            new CsvContenidoRepository(carpeta);

        // Crear pelicula y actor.
        Pelicula pelicula = new Pelicula(
            "Ciencia; ficción \"especial\" ñ",
            120,
            "Ciencia ficción",
            "Estudio; del Sur"
        );

        pelicula.agregarActor(
            new Actor("Ana Ñañez", "Ecuatoriana", 30)
        );

        // Crear serie y temporada.
        SerieDeTV serie = new SerieDeTV(
            "Serie de prueba", 45, "Drama"
        );

        serie.agregarTemporada(1, 2024, 8);

        // Crear documental e investigador.
        Documental documental = new Documental(
            "El universo", 90, "Ciencia", "Astronomia"
        );

        documental.agregarInvestigador(
            new Investigador(
                "Carlos Perez", "Astronomia", "UPS"
            )
        );

        // Crear video musical y participante.
        VideoMusical video = new VideoMusical(
            "Musica del futuro",
            5,
            "Electronica",
            "Artista principal",
            "Sello musical",
            2025
        );

        video.agregarActorParticipante(
            new Actor("Luis Gomez", "Ecuatoriana", 25)
        );

        // Crear conferencia e investigador.
        ConferenciaCientifica conferencia =
            new ConferenciaCientifica(
                "Inteligencia artificial",
                60,
                "Educativo",
                "Innovacion tecnologica",
                "Universidad",
                "25/11/2026",
                "Presencial"
            );

        conferencia.agregarInvestigador(
            new Investigador(
                "Maria Lopez",
                "Inteligencia artificial",
                "UPS"
            )
        );

        List<ContenidoAudiovisual> originales =
            Arrays.<ContenidoAudiovisual>asList(
                pelicula,
                serie,
                documental,
                video,
                conferencia
            );

        // Guardar los cinco contenidos.
        repositorio.guardar(originales);

        // Recuperar los contenidos.
        List<ContenidoAudiovisual> recuperados =
            repositorio.cargar();

        assertEquals(5, recuperados.size());

        // Organizar los contenidos por ID.
        Map<Integer, ContenidoAudiovisual> porId =
            new HashMap<>();

        for (ContenidoAudiovisual contenido : recuperados) {
            porId.put(contenido.getId(), contenido);
        }

        // Comprobar pelicula.
        assertTrue(porId.get(pelicula.getId()) instanceof Pelicula);

        Pelicula peliculaCargada =
            (Pelicula) porId.get(pelicula.getId());

        assertEquals(pelicula.getTitulo(),
            peliculaCargada.getTitulo());

        assertEquals(pelicula.getId(),
            peliculaCargada.getId());

        assertEquals("Estudio; del Sur",
            peliculaCargada.getEstudio());

        assertEquals(1, peliculaCargada.getActores().size());

        // Comprobar serie.
        assertTrue(porId.get(serie.getId()) instanceof SerieDeTV);

        SerieDeTV serieCargada =
            (SerieDeTV) porId.get(serie.getId());

        assertEquals(1,
            serieCargada.getListaTemporadas().size());

        // Comprobar documental.
        assertTrue(
            porId.get(documental.getId()) instanceof Documental
        );

        Documental documentalCargado =
            (Documental) porId.get(documental.getId());

        assertEquals("Astronomia", documentalCargado.getTema());

        assertEquals(1,
            documentalCargado.getInvestigadores().size());

        // Comprobar video musical.
        assertTrue(
            porId.get(video.getId()) instanceof VideoMusical
        );

        VideoMusical videoCargado =
            (VideoMusical) porId.get(video.getId());

        assertEquals("Artista principal",
            videoCargado.getArtistaPrincipal());

        assertEquals(1,
            videoCargado.getActoresParticipantes().size());

        // Comprobar conferencia.
        assertTrue(
            porId.get(conferencia.getId())
                instanceof ConferenciaCientifica
        );

        ConferenciaCientifica conferenciaCargada =
            (ConferenciaCientifica)
                porId.get(conferencia.getId());

        assertEquals("Innovacion tecnologica",
            conferenciaCargada.getTemaCentral());

        assertEquals(1,
            conferenciaCargada.getInvestigadores().size());
    }


// Comprueba que un directorio sin archivos CSV produzca una lista vacia.
    
    @Test
    public void cargarSinArchivosDevuelveListaVacia()
            throws Exception {

        Path carpeta = carpetaTemporal
            .newFolder("datosVacios").toPath();

        CsvContenidoRepository repositorio =
            new CsvContenidoRepository(carpeta);

        List<ContenidoAudiovisual> contenidos =
            repositorio.cargar();

        assertNotNull(contenidos);
        assertTrue(contenidos.isEmpty());
    }

   
// Comprueba el manejo de identificadores incorrectos dentro del archivo de contenidos.

    @Test
    public void cargarConIdentificadorIncorrectoLanzaExcepcion()
            throws Exception {

        Path carpeta = carpetaTemporal
            .newFolder("datosIncorrectos").toPath();

        Path archivo = carpeta.resolve("contenidos.csv");

        List<String> lineas = new ArrayList<>();

        lineas.add(
            "tipo;id;titulo;duracion;genero;dato1;dato2;dato3;dato4"
        );

        lineas.add(
            "PELICULA;ABC;Titulo;120;Drama;Estudio;;;"
        );

        Files.write(
            archivo,
            lineas,
            StandardCharsets.UTF_8
        );

        CsvContenidoRepository repositorio =
            new CsvContenidoRepository(carpeta);

        try {

            repositorio.cargar();

            fail("Se esperaba una IOException por el ID incorrecto.");

        } catch (IOException e) {

            assertTrue(
                e.getMessage().contains("ID del contenido")
            );
        }
    }
}
