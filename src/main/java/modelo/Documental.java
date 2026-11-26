package modelo;

import java.util.ArrayList;
import java.util.List;

// Subclase Documental que extiende de ContenidoAudiovisual
public class Documental extends ContenidoAudiovisual {

    private String tema;

    // Agregacion con la clase Investigador
    private List<Investigador> investigadores;

    // Constructor
    public Documental(String titulo, int duracionEnMinutos,
                      String genero, String tema) {

        super(titulo, duracionEnMinutos, genero);

        this.tema = tema;
        this.investigadores = new ArrayList<>();
    }

    // Getter y Setter del tema
    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    // Agregar un investigador al documental
    public void agregarInvestigador(Investigador investigador) {

        if (investigador != null && !investigadores.contains(investigador)) {
            investigadores.add(investigador);
        }
    }

    // Eliminar un investigador del documental
    public void eliminarInvestigador(Investigador investigador) {

        investigadores.remove(investigador);
    }

    // Obtener los investigadores del documental
    public List<Investigador> getInvestigadores() {

        return new ArrayList<>(investigadores);
    }

}