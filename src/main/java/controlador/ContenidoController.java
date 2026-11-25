
package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modelo.ContenidoAudiovisual;

import persistencia.ContenidoReader;
import persistencia.ContenidoWriter;
import persistencia.ContenidoRepository;


//Administra los contenidos audiovisuales y coordina las operaciones de lectura y escritura.

public class ContenidoController {

	private final List<ContenidoAudiovisual> contenidos;

	private final ContenidoWriter escritor;

// Constructor que recibe un repositorio completo.

    public ContenidoController(ContenidoRepository repositorio)
            throws IOException {

        this(repositorio, repositorio);
    }

    
// Constructor con dependencias independientes para lectura y escritura.

    public ContenidoController(
            ContenidoReader lector,
            ContenidoWriter escritor) throws IOException {

        if (lector == null || escritor == null) {

            throw new IllegalArgumentException(
                "Las dependencias no pueden ser nulas."
            );
        }

        this.escritor = escritor;

        this.contenidos = new ArrayList<>(
            lector.cargar()
        );
    }


// Registra un contenido y guarda los cambios.

    public void registrarContenido(ContenidoAudiovisual contenido)
            throws IOException {

        if (contenido == null) {

            throw new IllegalArgumentException(
                "El contenido no puede ser nulo."
            );
        }

        contenidos.add(contenido);

        try {

            escritor.guardar(contenidos);

        } catch (IOException e) {

            contenidos.remove(contenidos.size() - 1);

            throw e;
        }
    }


// Devuelve una copia de los contenidos registrados.

    public List<ContenidoAudiovisual> obtenerContenidos() {

        return new ArrayList<>(contenidos);
    }


// Busca un contenido mediante su identificador.

    public ContenidoAudiovisual buscarContenido(int id) {

        for (ContenidoAudiovisual contenido : contenidos) {

            if (contenido.getId() == id) {

                return contenido;
            }
        }

        return null;
    }

    
// Elimina un contenido y guarda los cambios.

    public boolean eliminarContenido(int id) throws IOException {

        for (int i = 0; i < contenidos.size(); i++) {

            ContenidoAudiovisual contenido = contenidos.get(i);

            if (contenido.getId() == id) {

                contenidos.remove(i);

                try {

                    escritor.guardar(contenidos);

                    return true;

                } catch (IOException e) {

                    contenidos.add(i, contenido);

                    throw e;
                }
            }
        }

        return false;
    }


// Consulta la cantidad de contenidos registrados.

    public int obtenerCantidad() {

        return contenidos.size();
    }
}
