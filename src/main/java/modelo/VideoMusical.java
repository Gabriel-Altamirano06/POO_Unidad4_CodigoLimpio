package modelo;

import java.util.ArrayList;
import java.util.List;

// Subclase VideoMusical que hereda de ContenidoAudiovisual
public class VideoMusical extends ContenidoAudiovisual {

    // Atributos propios
    private String artistaPrincipal;
    private String selloDiscografico;
    private int anioLanzamiento;

    // Conexion con la clase Actor
    private List<Actor> actoresParticipantes;

    // Constructor
    public VideoMusical(String titulo, int duracionEnMinutos,
                        String genero, String artistaPrincipal,
                        String selloDiscografico, int anioLanzamiento) {

        super(titulo, duracionEnMinutos, genero);

        this.artistaPrincipal = artistaPrincipal;
        this.selloDiscografico = selloDiscografico;
        this.anioLanzamiento = anioLanzamiento;

        this.actoresParticipantes = new ArrayList<>();
    }

    // Getters y setters
    public String getArtistaPrincipal() {
        return artistaPrincipal;
    }

    public void setArtistaPrincipal(String artistaPrincipal) {
        this.artistaPrincipal = artistaPrincipal;
    }

    public String getSelloDiscografico() {
        return selloDiscografico;
    }

    public void setSelloDiscografico(String selloDiscografico) {
        this.selloDiscografico = selloDiscografico;
    }

    public int getAnioLanzamiento() {
        return anioLanzamiento;
    }

    public void setAnioLanzamiento(int anioLanzamiento) {
        this.anioLanzamiento = anioLanzamiento;
    }

    // Agregar un actor participante
    public void agregarActorParticipante(Actor actor) {

        if (actor != null && !actoresParticipantes.contains(actor)) {
            actoresParticipantes.add(actor);
        }
    }

    // Eliminar un actor participante
    public void eliminarActorParticipante(Actor actor) {
        actoresParticipantes.remove(actor);
    }

    // Obtener los actores participantes
    public List<Actor> getActoresParticipantes() {
        return new ArrayList<>(actoresParticipantes);
    }

}