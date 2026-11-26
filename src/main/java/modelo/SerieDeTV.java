package modelo;

import java.util.ArrayList;
import java.util.List;

// Subclase SerieDeTV que extiende de ContenidoAudiovisual
public class SerieDeTV extends ContenidoAudiovisual {

    // Composicion con la clase Temporada
    private List<Temporada> listaTemporadas;

    // Constructor
    public SerieDeTV(String titulo, int duracionEnMinutos, String genero) {

        super(titulo, duracionEnMinutos, genero);

        this.listaTemporadas = new ArrayList<>();
    }


// Agrega una temporada validando sus datos y evitando numeros duplicados.

public void agregarTemporada(
        int numeroTemporada,
        int anioEstreno,
        int numeroEpisodios) {

    if (numeroTemporada <= 0
            || anioEstreno <= 0
            || numeroEpisodios <= 0) {

        throw new IllegalArgumentException(
            "Los datos de la temporada no son validos."
        );
    }

    for (Temporada temporada : listaTemporadas) {

        if (temporada.getNumeroTemporada() == numeroTemporada) {

            throw new IllegalArgumentException(
                "La temporada " + numeroTemporada
                + " ya existe en esta serie."
            );
        }
    }

    Temporada nuevaTemporada = new Temporada(
        numeroTemporada,
        anioEstreno,
        numeroEpisodios
    );

    listaTemporadas.add(nuevaTemporada);
}


    // Eliminar una temporada
    public void eliminarTemporada(int numeroTemporada) {

        for (int i = 0; i < listaTemporadas.size(); i++) {

            if (listaTemporadas.get(i).getNumeroTemporada()
                    == numeroTemporada) {

                listaTemporadas.remove(i);
                return;
            }
        }
    }

    // Obtener la cantidad de temporadas
    public int getTemporadas() {
        return listaTemporadas.size();
    }

    // Obtener la lista de temporadas
    public List<Temporada> getListaTemporadas() {
        return new ArrayList<>(listaTemporadas);
    }

}