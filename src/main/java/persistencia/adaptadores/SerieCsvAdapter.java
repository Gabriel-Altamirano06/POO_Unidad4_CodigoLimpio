
package persistencia.adaptadores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.SerieDeTV;
import modelo.Temporada;


// Adaptador encargado de guardar y recuperar series de television y sus temporadas.

public class SerieCsvAdapter
        implements ContenidoCsvAdapter<SerieDeTV> {


// Identifica el tipo de contenido.

    @Override
    public String obtenerTipo() {

        return "SERIE";
    }


// Devuelve la clase administrada por este adaptador.

    @Override
    public Class<SerieDeTV> obtenerClase() {

        return SerieDeTV.class;
    }

 // Las series no necesitan datos adicionales en el archivo principal de contenidos.

    @Override
    public String[] obtenerDatosEspecificos(SerieDeTV serie) {

        return new String[] {
            "", "", "", ""
        };
    }


// Reconstruye una serie a partir de sus datos CSV.

    @Override
    public SerieDeTV crearContenido(
            String titulo,
            int duracion,
            String genero,
            String[] datos) throws IOException {

        if (datos == null || datos.length != 4) {

            throw new IOException(
                "Los datos de la serie son incorrectos."
            );
        }

        return new SerieDeTV(
            titulo,
            duracion,
            genero
        );
    }

    
// Convierte las temporadas de una serie en filas para el archivo temporadas.csv.

    @Override
    public List<String[]> obtenerFilasTemporadas(
            SerieDeTV serie,
            int idContenido) {

        List<String[]> filas = new ArrayList<>();

        for (Temporada temporada : serie.getListaTemporadas()) {

            filas.add(new String[] {
                String.valueOf(idContenido),

                String.valueOf(
                    temporada.getNumeroTemporada()
                ),

                String.valueOf(
                    temporada.getAnioEstreno()
                ),

                String.valueOf(
                    temporada.getNumeroEpisodios()
                )
            });
        }

        return filas;
    }


// Recupera una temporada y la agrega a su serie.

    @Override
    public void agregarTemporada(
            SerieDeTV serie,
            Temporada temporada) throws IOException {

        if (serie == null || temporada == null) {

            throw new IllegalArgumentException(
                "La serie y la temporada no pueden ser nulas."
            );
        }

        if (temporada.getNumeroTemporada() <= 0
                || temporada.getAnioEstreno() <= 0
                || temporada.getNumeroEpisodios() <= 0) {

            throw new IOException(
                "Los datos de la temporada no son validos."
            );
        }

        for (Temporada existente : serie.getListaTemporadas()) {

            if (existente.getNumeroTemporada()
                    == temporada.getNumeroTemporada()) {

                throw new IOException(
                    "La temporada ya existe en la serie."
                );
            }
        }

        serie.agregarTemporada(
            temporada.getNumeroTemporada(),
            temporada.getAnioEstreno(),
            temporada.getNumeroEpisodios()
        );
    }
}
