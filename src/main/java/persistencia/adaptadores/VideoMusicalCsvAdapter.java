
package persistencia.adaptadores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.Actor;
import modelo.VideoMusical;

// Adaptador encargado de guardar y recuperar videos musicales y sus participantes.

public class VideoMusicalCsvAdapter
        implements ContenidoCsvAdapter<VideoMusical> {

    @Override
    public String obtenerTipo() {
        return "VIDEO_MUSICAL";
    }

    @Override
    public Class<VideoMusical> obtenerClase() {
        return VideoMusical.class;
    }

    @Override
    public String[] obtenerDatosEspecificos(VideoMusical video) {

        return new String[] {
            video.getArtistaPrincipal(),
            video.getSelloDiscografico(),
            String.valueOf(video.getAnioLanzamiento()),
            ""
        };
    }

    @Override
    public VideoMusical crearContenido(
            String titulo,
            int duracion,
            String genero,
            String[] datos) throws IOException {

        if (datos == null || datos.length != 4) {
            throw new IOException(
                "Los datos del video musical son incorrectos."
            );
        }

        int anio;

        try {
            anio = Integer.parseInt(datos[2].trim());

        } catch (NumberFormatException e) {

            throw new IOException(
                "El año de lanzamiento es incorrecto.", e
            );
        }

        return new VideoMusical(
            titulo,
            duracion,
            genero,
            datos[0],
            datos[1],
            anio
        );
    }

    @Override
    public List<String[]> obtenerFilasActores(
            VideoMusical video,
            int idContenido) {

        List<String[]> filas = new ArrayList<>();

        for (Actor actor : video.getActoresParticipantes()) {

            filas.add(new String[] {
                String.valueOf(idContenido),
                "VIDEO_MUSICAL",
                actor.getNombre(),
                actor.getNacionalidad(),
                String.valueOf(actor.getEdad())
            });
        }

        return filas;
    }

    @Override
    public void agregarActor(
            VideoMusical video,
            String tipoRelacion,
            Actor actor) throws IOException {

        if (!"VIDEO_MUSICAL".equals(tipoRelacion)) {
            throw new IOException(
                "La relacion no corresponde a un video musical."
            );
        }

        if (video == null || actor == null) {
            throw new IllegalArgumentException(
                "El video y el actor no pueden ser nulos."
            );
        }

        video.agregarActorParticipante(actor);
    }
}
