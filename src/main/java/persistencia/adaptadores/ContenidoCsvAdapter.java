
package persistencia.adaptadores;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import modelo.Actor;
import modelo.ContenidoAudiovisual;
import modelo.Investigador;
import modelo.Temporada;

//  Define como cada tipo de contenido se convierteen informacion CSV y como se recuperan sus relaciones.

public interface ContenidoCsvAdapter<T extends ContenidoAudiovisual> {

    String obtenerTipo();

    Class<T> obtenerClase();

    String[] obtenerDatosEspecificos(T contenido);

    T crearContenido(
            String titulo,
            int duracion,
            String genero,
            String[] datos
    ) throws IOException;

    default List<String[]> obtenerFilasActores(
            T contenido, int idContenido) {

        return Collections.emptyList();
    }

    default List<String[]> obtenerFilasTemporadas(
            T contenido, int idContenido) {

        return Collections.emptyList();
    }

    default List<String[]> obtenerFilasInvestigadores(
            T contenido, int idContenido) {

        return Collections.emptyList();
    }

    default void agregarActor(
            T contenido,
            String tipoRelacion,
            Actor actor
    ) throws IOException {

        throw new IOException(
            "Este contenido no admite actores relacionados."
        );
    }

    default void agregarTemporada(
            T contenido,
            Temporada temporada
    ) throws IOException {

        throw new IOException(
            "Este contenido no admite temporadas."
        );
    }

    default void agregarInvestigador(
            T contenido,
            String tipoRelacion,
            Investigador investigador
    ) throws IOException {

        throw new IOException(
            "Este contenido no admite investigadores relacionados."
        );
    }
}
