
package persistencia.adaptadores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.Documental;
import modelo.Investigador;


// Adaptador encargado de guardar y recuperar documentales y sus investigadores.

public class DocumentalCsvAdapter
        implements ContenidoCsvAdapter<Documental> {


// Identifica el tipo de contenido.

    @Override
    public String obtenerTipo() {

        return "DOCUMENTAL";
    }


// Devuelve la clase administrada por este adaptador.

    @Override
    public Class<Documental> obtenerClase() {

        return Documental.class;
    }


// Obtiene los datos especificos del documental.

    @Override
    public String[] obtenerDatosEspecificos(
            Documental documental) {

        return new String[] {
            documental.getTema(),
            "",
            "",
            ""
        };
    }


// Reconstruye un documental utilizando los datos CSV.

    @Override
    public Documental crearContenido(
            String titulo,
            int duracion,
            String genero,
            String[] datos) throws IOException {

        if (datos == null || datos.length != 4) {

            throw new IOException(
                "Los datos del documental son incorrectos."
            );
        }

        return new Documental(
            titulo,
            duracion,
            genero,
            datos[0]
        );
    }


// Convierte los investigadores en filas CSV.

    @Override
    public List<String[]> obtenerFilasInvestigadores(
            Documental documental,
            int idContenido) {

        List<String[]> filas = new ArrayList<>();

        for (Investigador investigador :
                documental.getInvestigadores()) {

            filas.add(new String[] {

                String.valueOf(idContenido),
                "DOCUMENTAL",
                investigador.getNombre(),
                investigador.getEspecialidad(),
                investigador.getInstitucion()

            });
        }

        return filas;
    }


// Recupera un investigador y lo asocia al documental correspondiente.

    @Override
    public void agregarInvestigador(
            Documental documental,
            String tipoRelacion,
            Investigador investigador) throws IOException {

        if (!"DOCUMENTAL".equals(tipoRelacion)) {

            throw new IOException(
                "La relacion del investigador "
                + "no corresponde a un documental."
            );
        }

        if (investigador == null) {

            throw new IllegalArgumentException(
                "El investigador no puede ser nulo."
            );
        }

        documental.agregarInvestigador(investigador);
    }
}
