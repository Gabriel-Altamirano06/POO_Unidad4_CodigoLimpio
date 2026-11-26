
package persistencia.adaptadores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.Actor;
import modelo.Pelicula;


// Adaptador encargado de guardar y recuperar la informacion especifica de las peliculas.

public class PeliculaCsvAdapter
        implements ContenidoCsvAdapter<Pelicula> {


// Identifica el tipo de contenido.
    @Override
    public String obtenerTipo() {
        return "PELICULA";
    }


// Devuelve la clase que administra este adaptador.

    @Override
    public Class<Pelicula> obtenerClase() {
        return Pelicula.class;
    }


// Obtiene los datos especificos de una pelicula.

    @Override
    public String[] obtenerDatosEspecificos(Pelicula pelicula) {

        return new String[] {
            pelicula.getEstudio(),
            "",
            "",
            ""
        };
    }


// Reconstruye una pelicula utilizando los datos CSV.

    @Override
    public Pelicula crearContenido(
            String titulo,
            int duracion,
            String genero,
            String[] datos) throws IOException {

        if (datos == null || datos.length != 4) {
            throw new IOException(
                "Los datos de la pelicula son incorrectos."
            );
        }

        return new Pelicula(
            titulo,
            duracion,
            genero,
            datos[0]
        );
    }


// Convierte los actores de una pelicula en filas que se almacenaran en actores.csv.

    @Override
    public List<String[]> obtenerFilasActores(
            Pelicula pelicula, int idContenido) {

        List<String[]> filas = new ArrayList<>();

        for (Actor actor : pelicula.getActores()) {

            filas.add(new String[] {
                String.valueOf(idContenido),
                "PELICULA",
                actor.getNombre(),
                actor.getNacionalidad(),
                String.valueOf(actor.getEdad())
            });
        }

        return filas;
    }


// Recupera un actor y lo agrega a su pelicula.

    @Override
    public void agregarActor(
            Pelicula pelicula,
            String tipoRelacion,
            Actor actor) throws IOException {

        if (!"PELICULA".equals(tipoRelacion)) {

            throw new IOException(
                "La relacion del actor no corresponde a una pelicula."
            );
        }

        if (actor == null) {

            throw new IllegalArgumentException(
                "El actor no puede ser nulo."
            );
        }

        pelicula.agregarActor(actor);
    }
}
