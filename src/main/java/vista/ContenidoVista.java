
package vista;

import java.util.List;
import java.util.Scanner;

import modelo.Actor;
import modelo.ConferenciaCientifica;
import modelo.ContenidoAudiovisual;
import modelo.Documental;
import modelo.Investigador;
import modelo.Pelicula;
import modelo.SerieDeTV;
import modelo.Temporada;
import modelo.VideoMusical;


// Presenta los menus, recibe los datos y muestra la informacion de los contenidos audiovisuales.

public class ContenidoVista {

    private final Scanner entrada;

    public ContenidoVista() {
        entrada = new Scanner(System.in);
    }

    public void mostrarMenu() {

        System.out.println("\n========================================");
        System.out.println("       SISTEMA DE GESTION AUDIOVISUAL");
        System.out.println("========================================");
        System.out.println("1. Registrar pelicula");
        System.out.println("2. Registrar serie de television");
        System.out.println("3. Registrar documental");
        System.out.println("4. Registrar video musical");
        System.out.println("5. Registrar conferencia cientifica");
        System.out.println("6. Mostrar todos los contenidos");
        System.out.println("7. Buscar contenido por ID");
        System.out.println("8. Eliminar contenido");
        System.out.println("0. Salir");
        System.out.println("========================================");
    }

    public int leerOpcion() {

        return leerEntero("Seleccione una opcion: ");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }


// Presenta todos los contenidos mediante sus datos.

    public void mostrarContenidos(
            List<ContenidoAudiovisual> contenidos) {

        System.out.println("\n--- CONTENIDOS AUDIOVISUALES ---");

        if (contenidos == null || contenidos.isEmpty()) {

            mostrarMensaje("No existen contenidos registrados.");
            return;
        }

        for (ContenidoAudiovisual contenido : contenidos) {

            mostrarDetalleContenido(contenido);

            System.out.println("----------------------------------------");
        }
    }


// Determina el tipo de contenido y presenta sus datos.

    private void mostrarDetalleContenido(
            ContenidoAudiovisual contenido) {

        System.out.println("\nID: " + contenido.getId());
        System.out.println("Titulo: " + contenido.getTitulo());
        System.out.println("Duracion: "
                + contenido.getDuracionEnMinutos() + " minutos");
        System.out.println("Genero: " + contenido.getGenero());

        if (contenido instanceof Pelicula) {

            mostrarPelicula((Pelicula) contenido);

        } else if (contenido instanceof SerieDeTV) {

            mostrarSerie((SerieDeTV) contenido);

        } else if (contenido instanceof Documental) {

            mostrarDocumental((Documental) contenido);

        } else if (contenido instanceof VideoMusical) {

            mostrarVideoMusical((VideoMusical) contenido);

        } else if (contenido instanceof ConferenciaCientifica) {

            mostrarConferencia((ConferenciaCientifica) contenido);
        }
    }

    private void mostrarPelicula(Pelicula pelicula) {

        System.out.println("Tipo: Pelicula");
        System.out.println("Estudio: " + pelicula.getEstudio());

        System.out.println("\nActores participantes:");

        if (pelicula.getActores().isEmpty()) {

            System.out.println("No hay actores registrados.");

        } else {

            for (Actor actor : pelicula.getActores()) {
                mostrarActor(actor);
            }
        }
    }

    private void mostrarSerie(SerieDeTV serie) {

        System.out.println("Tipo: Serie de television");

        System.out.println("Cantidad de temporadas: "
                + serie.getListaTemporadas().size());

        System.out.println("\nInformacion de las temporadas:");

        if (serie.getListaTemporadas().isEmpty()) {

            System.out.println("No hay temporadas registradas.");

        } else {

            for (Temporada temporada : serie.getListaTemporadas()) {
                mostrarTemporada(temporada);
            }
        }
    }

    private void mostrarDocumental(Documental documental) {

        System.out.println("Tipo: Documental");
        System.out.println("Tema: " + documental.getTema());

        System.out.println("\nInvestigadores participantes:");

        if (documental.getInvestigadores().isEmpty()) {

            System.out.println("No hay investigadores registrados.");

        } else {

            for (Investigador investigador :
                    documental.getInvestigadores()) {

                mostrarInvestigador(investigador);
            }
        }
    }

    private void mostrarVideoMusical(VideoMusical video) {

        System.out.println("Tipo: Video musical");

        System.out.println("Artista principal: "
                + video.getArtistaPrincipal());

        System.out.println("Sello discografico: "
                + video.getSelloDiscografico());

        System.out.println("Anio de lanzamiento: "
                + video.getAnioLanzamiento());

        System.out.println("\nParticipantes:");

        if (video.getActoresParticipantes().isEmpty()) {

            System.out.println("No hay participantes registrados.");

        } else {

            for (Actor actor : video.getActoresParticipantes()) {
                mostrarActor(actor);
            }
        }
    }

    private void mostrarConferencia(
            ConferenciaCientifica conferencia) {

        System.out.println("Tipo: Conferencia cientifica");

        System.out.println("Tema central: "
                + conferencia.getTemaCentral());

        System.out.println("Institucion organizadora: "
                + conferencia.getInstitucionOrganizadora());

        System.out.println("Fecha de realizacion: "
                + conferencia.getFechaRealizacion());

        System.out.println("Modalidad: "
                + conferencia.getModalidad());

        System.out.println("\nInvestigadores participantes:");

        if (conferencia.getInvestigadores().isEmpty()) {

            System.out.println("No hay investigadores registrados.");

        } else {

            for (Investigador investigador :
                    conferencia.getInvestigadores()) {

                mostrarInvestigador(investigador);
            }
        }
    }

    private void mostrarActor(Actor actor) {

        System.out.println("Nombre: " + actor.getNombre());
        System.out.println("Nacionalidad: "
                + actor.getNacionalidad());
        System.out.println("Edad: " + actor.getEdad());
        System.out.println("-------------------------");
    }

    private void mostrarInvestigador(Investigador investigador) {

        System.out.println("Nombre: " + investigador.getNombre());

        System.out.println("Especialidad: "
                + investigador.getEspecialidad());

        System.out.println("Institucion: "
                + investigador.getInstitucion());

        System.out.println("-------------------------");
    }

    private void mostrarTemporada(Temporada temporada) {

        System.out.println("Numero de temporada: "
                + temporada.getNumeroTemporada());

        System.out.println("Anio de estreno: "
                + temporada.getAnioEstreno());

        System.out.println("Numero de episodios: "
                + temporada.getNumeroEpisodios());

        System.out.println("-------------------------");
    }

    public int leerIdentificador() {

        return leerEntero("Ingrese el ID del contenido: ");
    }

    public String leerTexto(String mensaje) {

        String texto;

        do {

            System.out.print(mensaje);
            texto = entrada.nextLine().trim();

            if (texto.isEmpty()) {
                mostrarMensaje("Este campo no puede estar vacio.");
            }

        } while (texto.isEmpty());

        return texto;
    }

    public int leerEntero(String mensaje) {

        while (true) {

            System.out.print(mensaje);
            String texto = entrada.nextLine().trim();

            try {

                return Integer.parseInt(texto);

            } catch (NumberFormatException e) {

                mostrarMensaje("Ingrese un numero entero valido.");
            }
        }
    }

    public int leerPositivo(String mensaje) {

        int numero;

        do {

            numero = leerEntero(mensaje);

            if (numero <= 0) {
                mostrarMensaje("El valor debe ser mayor que cero.");
            }

        } while (numero <= 0);

        return numero;
    }

    public int leerCantidad(String mensaje) {

        int cantidad;

        do {

            cantidad = leerEntero(mensaje);

            if (cantidad < 0) {
                mostrarMensaje("La cantidad no puede ser negativa.");
            }

        } while (cantidad < 0);

        return cantidad;
    }
}
