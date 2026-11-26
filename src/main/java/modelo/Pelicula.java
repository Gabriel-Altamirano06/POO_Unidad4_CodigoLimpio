package modelo;

import java.util.ArrayList;
import java.util.List;

// Subclase Pelicula que extiende de ContenidoAudiovisual
public class Pelicula extends ContenidoAudiovisual {

    private String estudio;

    // Conexion con la clase Actor
    private List<Actor> actores;

    // Constructor
    public Pelicula(String titulo, int duracionEnMinutos,
                    String genero, String estudio) {

        super(titulo, duracionEnMinutos, genero);

        this.estudio = estudio;
        this.actores = new ArrayList<>();
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    // Agregar un actor a la pelicula
    public void agregarActor(Actor actor) {
        if (actor != null && !actores.contains(actor)) {
            actores.add(actor);
        }
    }

    // Eliminar un actor de la pelicula
    public void eliminarActor(Actor actor) {
        actores.remove(actor);
    }

    // Obtener los actores de la pelicula
    public List<Actor> getActores() {
        return new ArrayList<>(actores);
    }

     
}