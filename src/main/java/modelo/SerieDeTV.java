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

    // Agregar una nueva temporada
    public void agregarTemporada(int numeroTemporada,
                                 int anioEstreno,
                                 int numeroEpisodios) {

        if (numeroTemporada <= 0 || anioEstreno <= 0
                || numeroEpisodios <= 0) {

            System.out.println("Los datos de la temporada no son validos.");
            return;
        }

        // Evitar numeros de temporada duplicados
        for (Temporada temporada : listaTemporadas) {

            if (temporada.getNumeroTemporada() == numeroTemporada) {
                System.out.println("La temporada ya existe.");
                return;
            }
        }

        // Crear el objeto Temporada dentro de SerieDeTV
        Temporada nuevaTemporada = new Temporada(
                numeroTemporada, anioEstreno, numeroEpisodios
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