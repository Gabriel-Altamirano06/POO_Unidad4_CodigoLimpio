
package persistencia.adaptadores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.ConferenciaCientifica;
import modelo.Investigador;


// Adaptador encargado de guardar y recuperar conferencias cientificas y sus investigadores.

public class ConferenciaCsvAdapter
        implements ContenidoCsvAdapter<ConferenciaCientifica> {


// Identifica el tipo de contenido.

    @Override
    public String obtenerTipo() {
        return "CONFERENCIA";
    }


// Devuelve la clase administrada por este adaptador.

    @Override
    public Class<ConferenciaCientifica> obtenerClase() {
        return ConferenciaCientifica.class;
    }


// Obtiene los cuatro datos especificos de una conferencia cientifica.

    @Override
    public String[] obtenerDatosEspecificos(
            ConferenciaCientifica conferencia) {

        return new String[] {
            conferencia.getTemaCentral(),
            conferencia.getInstitucionOrganizadora(),
            conferencia.getFechaRealizacion(),
            conferencia.getModalidad()
        };
    }


// Reconstruye una conferencia utilizando los datos CSV.

    @Override
    public ConferenciaCientifica crearContenido(
            String titulo,
            int duracion,
            String genero,
            String[] datos) throws IOException {

        if (datos == null || datos.length != 4) {
            throw new IOException(
                "Los datos de la conferencia son incorrectos."
            );
        }

        return new ConferenciaCientifica(
            titulo,
            duracion,
            genero,
            datos[0],
            datos[1],
            datos[2],
            datos[3]
        );
    }

   
// Convierte los investigadores en filas CSV.

    @Override
    public List<String[]> obtenerFilasInvestigadores(
            ConferenciaCientifica conferencia,
            int idContenido) {

        List<String[]> filas = new ArrayList<>();

        for (Investigador investigador :
                conferencia.getInvestigadores()) {

            filas.add(new String[] {
                String.valueOf(idContenido),
                "CONFERENCIA",
                investigador.getNombre(),
                investigador.getEspecialidad(),
                investigador.getInstitucion()
            });
        }

        return filas;
    }


// Recupera un investigador y lo relaciona con su conferencia correspondiente.

    @Override
    public void agregarInvestigador(
            ConferenciaCientifica conferencia,
            String tipoRelacion,
            Investigador investigador) throws IOException {

        if (!"CONFERENCIA".equals(tipoRelacion)) {

            throw new IOException(
                "La relacion del investigador "
                + "no corresponde a una conferencia."
            );
        }

        if (conferencia == null || investigador == null) {

            throw new IllegalArgumentException(
                "La conferencia y el investigador "
                + "no pueden ser nulos."
            );
        }

        conferencia.agregarInvestigador(investigador);
    }
}
