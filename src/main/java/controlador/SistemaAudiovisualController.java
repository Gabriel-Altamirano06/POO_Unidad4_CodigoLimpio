
package controlador;

import java.io.IOException;

import persistencia.ContenidoRepository;
import persistencia.CsvContenidoRepository;

import java.util.Collections;

import modelo.Actor;
import modelo.ContenidoAudiovisual;
import modelo.Pelicula;
import modelo.SerieDeTV;
import modelo.Documental;
import modelo.Investigador;
import modelo.VideoMusical;
import modelo.ConferenciaCientifica;

import vista.ContenidoVista;


// Coordina las operaciones entre la vista y el modelo.

public class SistemaAudiovisualController {

    private final ContenidoController gestor;
    private final ContenidoVista vista;

    public SistemaAudiovisualController() throws IOException {
        this(new CsvContenidoRepository());
    }

    public SistemaAudiovisualController(
            ContenidoRepository repositorio) throws IOException {

        gestor = new ContenidoController(repositorio);
        vista = new ContenidoVista();

        vista.mostrarMensaje(
            "Se cargaron " + gestor.obtenerCantidad()
            + " contenidos desde los archivos CSV."
        );
    }


// Inicia el funcionamiento del sistema.

    public void iniciar() {

        int opcion;

        do {
            vista.mostrarMenu();
            opcion = vista.leerOpcion();

            procesarOpcion(opcion);

        } while (opcion != 0);
    }


// Distribuye las opciones seleccionadas.

    private void procesarOpcion(int opcion) {

        switch (opcion) {

            case 1:
                registrarPelicula();
                break;

            case 2:
                registrarSerie();
                break;

            case 3:
                registrarDocumental();
                break;

            case 4:
                registrarVideoMusical();
                break;

            case 5:
                registrarConferencia();
                break;

            case 6:
                mostrarContenidos();
                break;

            case 7:
                buscarContenido();
                break;

            case 8:
                eliminarContenido();
                break;

            case 0:
                vista.mostrarMensaje(
                    "Gracias por utilizar el sistema."
                );
                break;

            default:
                vista.mostrarMensaje(
                    "Opcion no valida. Intente nuevamente."
                );
        }
    }


// Registra una pelicula con sus actores.

    private void registrarPelicula() {

        vista.mostrarMensaje("\n--- REGISTRO DE PELICULA ---");

        String titulo = vista.leerTexto("Titulo: ");
        int duracion = vista.leerPositivo(
            "Duracion en minutos: "
        );
        String genero = vista.leerTexto("Genero: ");
        String estudio = vista.leerTexto(
            "Estudio cinematografico: "
        );

        Pelicula pelicula = new Pelicula(
            titulo, duracion, genero, estudio
        );

        int cantidad = vista.leerCantidad(
            "Cantidad de actores: "
        );

        for (int i = 0; i < cantidad; i++) {
            pelicula.agregarActor(
                solicitarActor("actor " + (i + 1))
            );
        }

        registrarYGuardar(pelicula);
    }


// Registra una serie con sus temporadas.

private void registrarSerie() {

    vista.mostrarMensaje("\n--- REGISTRO DE SERIE ---");

    String titulo = vista.leerTexto("Titulo: ");

    int duracion = vista.leerPositivo(
        "Duracion en minutos: "
    );

    String genero = vista.leerTexto("Genero: ");

    SerieDeTV serie = new SerieDeTV(
        titulo, duracion, genero
    );

    int cantidad = vista.leerCantidad(
        "Cantidad de temporadas: "
    );

    for (int i = 0; i < cantidad; i++) {

        vista.mostrarMensaje(
            "\nTemporada " + (i + 1)
        );

        int numero = vista.leerPositivo(
            "Numero de temporada: "
        );

        int anio = vista.leerPositivo(
            "Año de estreno: "
        );

        int episodios = vista.leerPositivo(
            "Numero de episodios: "
        );

        try {

            serie.agregarTemporada(
                numero, anio, episodios
            );

            vista.mostrarMensaje(
                "Temporada agregada correctamente."
            );

        } catch (IllegalArgumentException e) {

            vista.mostrarMensaje(
                "No se pudo agregar la temporada: "
                + e.getMessage()
            );

            i--;
        }
    }

    registrarYGuardar(serie);
}


// Registra un documental con sus investigadores.

    private void registrarDocumental() {

        vista.mostrarMensaje("\n--- REGISTRO DE DOCUMENTAL ---");

        String titulo = vista.leerTexto("Titulo: ");
        int duracion = vista.leerPositivo(
            "Duracion en minutos: "
        );
        String genero = vista.leerTexto("Genero: ");
        String tema = vista.leerTexto(
            "Tema del documental: "
        );

        Documental documental = new Documental(
            titulo, duracion, genero, tema
        );

        int cantidad = vista.leerCantidad(
            "Cantidad de investigadores: "
        );

        for (int i = 0; i < cantidad; i++) {

            documental.agregarInvestigador(
                solicitarInvestigador("investigador " + (i + 1))
            );
        }

        registrarYGuardar(documental);

    }


// Registra un video musical con sus participantes.

    private void registrarVideoMusical() {

        vista.mostrarMensaje("\n--- REGISTRO DE VIDEO MUSICAL ---");

        String titulo = vista.leerTexto("Titulo: ");
        int duracion = vista.leerPositivo(
            "Duracion en minutos: "
        );
        String genero = vista.leerTexto("Genero musical: ");
        String artista = vista.leerTexto(
            "Artista principal: "
        );
        String sello = vista.leerTexto(
            "Sello discografico: "
        );
        int anio = vista.leerPositivo(
            "Año de lanzamiento: "
        );

        VideoMusical video = new VideoMusical(
            titulo, duracion, genero, artista, sello, anio
        );

        int cantidad = vista.leerCantidad(
            "Cantidad de participantes: "
        );

        for (int i = 0; i < cantidad; i++) {

            video.agregarActorParticipante(
                solicitarActor("participante " + (i + 1))
            );
        }

        registrarYGuardar(video);

    }


// Registra una conferencia cientifica.

    private void registrarConferencia() {

        vista.mostrarMensaje(
            "\n--- REGISTRO DE CONFERENCIA CIENTIFICA ---"
        );

        String titulo = vista.leerTexto("Titulo: ");
        int duracion = vista.leerPositivo(
            "Duracion en minutos: "
        );
        String genero = vista.leerTexto("Genero: ");
        String tema = vista.leerTexto("Tema central: ");

        String institucion = vista.leerTexto(
            "Institucion organizadora: "
        );

        String fecha = vista.leerTexto(
            "Fecha de realizacion: "
        );

        String modalidad = vista.leerTexto(
            "Modalidad: "
        );

        ConferenciaCientifica conferencia =
            new ConferenciaCientifica(
                titulo,
                duracion,
                genero,
                tema,
                institucion,
                fecha,
                modalidad
            );

        int cantidad = vista.leerCantidad(
            "Cantidad de investigadores: "
        );

        for (int i = 0; i < cantidad; i++) {

            conferencia.agregarInvestigador(
                solicitarInvestigador("investigador " + (i + 1))
            );
        }

        registrarYGuardar(conferencia);

    }


// Solicita y construye un actor.

    private Actor solicitarActor(String descripcion) {

        vista.mostrarMensaje(
            "\nDatos del " + descripcion
        );

        String nombre = vista.leerTexto("Nombre: ");
        String nacionalidad = vista.leerTexto(
            "Nacionalidad: "
        );
        int edad = vista.leerPositivo("Edad: ");

        return new Actor(nombre, nacionalidad, edad);
    }


// Solicita y construye un investigador.

    private Investigador solicitarInvestigador(
            String descripcion) {

        vista.mostrarMensaje(
            "\nDatos del " + descripcion
        );

        String nombre = vista.leerTexto("Nombre: ");
        String especialidad = vista.leerTexto(
            "Especialidad: "
        );
        String institucion = vista.leerTexto(
            "Institucion: "
        );

        return new Investigador(
            nombre, especialidad, institucion
        );
    }

// Muestra los contenidos registrados.

    private void mostrarContenidos() {

        vista.mostrarContenidos(
            gestor.obtenerContenidos()
        );
    }


// Busca un contenido por su identificador.

    private void buscarContenido() {

        int id = vista.leerIdentificador();

        if (id < 0) {
            vista.mostrarMensaje("Identificador no valido.");
            return;
        }

        ContenidoAudiovisual contenido =
            gestor.buscarContenido(id);

        if (contenido == null) {

            vista.mostrarMensaje(
                "No se encontro el contenido solicitado."
            );

        } else {

            vista.mostrarContenidos(
                Collections.singletonList(contenido)
            );
        }
    }


// Elimina un contenido t guarda la modificacion..


    private void eliminarContenido() {

        int id = vista.leerIdentificador();

        if (id < 0) {
            vista.mostrarMensaje("Identificador no valido.");
            return;
        }

        try {

            boolean eliminado = gestor.eliminarContenido(id);

            if (eliminado) {

                vista.mostrarMensaje(
                    "Contenido eliminado y cambios guardados."
                );

            } else {

                vista.mostrarMensaje(
                    "No existe un contenido con ese identificador."
                );
            }

        } catch (IOException e) {

            vista.mostrarMensaje(
                "No se pudo guardar la eliminación: "
                + e.getMessage()
            );
        }
    }

// Registra un contenido y confirma su almacenamiento.

    private void registrarYGuardar(ContenidoAudiovisual contenido) {

        try {

            gestor.registrarContenido(contenido);

            vista.mostrarMensaje(
                "Contenido guardado correctamente. ID: "
                + contenido.getId()
            );

        } catch (IOException e) {

            vista.mostrarMensaje(
                "Error al guardar el contenido: " + e.getMessage()
            );
        }
    }
    
    
    
    
    
    
}
