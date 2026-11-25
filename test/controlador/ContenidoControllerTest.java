
package controlador;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import modelo.ContenidoAudiovisual;
import modelo.Pelicula;
import persistencia.ContenidoRepository;

// Prueba las operaciones del controlador de contenidos.

public class ContenidoControllerTest {

   
// Comprueba que un contenido se registre correctamente.
 
    @Test
    public void registrarContenidoCorrectamente() throws IOException {

        RepositorioMemoria repositorio = new RepositorioMemoria();

        ContenidoController controlador =
            new ContenidoController(repositorio);

        Pelicula pelicula = crearPelicula();

        controlador.registrarContenido(pelicula);

        assertEquals(1, controlador.obtenerCantidad());

        assertNotNull(
            controlador.buscarContenido(pelicula.getId())
        );

        assertEquals(1, repositorio.contenidos.size());
    }

    
// Comprueba que se pueda buscar un contenido por su ID.

    @Test
    public void buscarContenidoPorIdentificador() throws IOException {

        ContenidoController controlador =
            new ContenidoController(new RepositorioMemoria());

        Pelicula pelicula = crearPelicula();

        controlador.registrarContenido(pelicula);

        ContenidoAudiovisual encontrado =
            controlador.buscarContenido(pelicula.getId());

        assertNotNull(encontrado);
        assertEquals(pelicula.getId(), encontrado.getId());
        assertEquals("Interestelar", encontrado.getTitulo());
    }

    
// Comprueba que un contenido se elimine correctamente.

    @Test
    public void eliminarContenidoCorrectamente() throws IOException {

        RepositorioMemoria repositorio = new RepositorioMemoria();

        ContenidoController controlador =
            new ContenidoController(repositorio);

        Pelicula pelicula = crearPelicula();

        controlador.registrarContenido(pelicula);

        boolean resultado =
            controlador.eliminarContenido(pelicula.getId());

        assertTrue(resultado);
        assertEquals(0, controlador.obtenerCantidad());
        assertNull(
            controlador.buscarContenido(pelicula.getId())
        );
        assertTrue(repositorio.contenidos.isEmpty());
    }

    
// Comprueba que no se puedan registrar valores nulos.

    @Test
    public void rechazarContenidoNulo() throws IOException {

        ContenidoController controlador =
            new ContenidoController(new RepositorioMemoria());

        try {

            controlador.registrarContenido(null);

            fail("Se esperaba una IllegalArgumentException.");

        } catch (IllegalArgumentException e) {

            assertEquals(
                "El contenido no puede ser nulo.",
                e.getMessage()
            );
        }

        assertEquals(0, controlador.obtenerCantidad());
    }

    
// Comprueba que el controlador restaure la lista cuando falla la escritura durante el registro.

    @Test
    public void restaurarListaCuandoFallaElRegistro()
            throws IOException {

        RepositorioMemoria repositorio = new RepositorioMemoria();

        ContenidoController controlador =
            new ContenidoController(repositorio);

        repositorio.activarErrorDeGuardado();

        try {

            controlador.registrarContenido(crearPelicula());

            fail("Se esperaba una IOException.");

        } catch (IOException e) {

            assertEquals(
                "Error simulado de escritura.",
                e.getMessage()
            );
        }

        assertEquals(0, controlador.obtenerCantidad());
        assertTrue(repositorio.contenidos.isEmpty());
    }


// Comprueba que un contenido se restaure en memoria cuando falla la escritura durante su eliminacion.
    
    @Test
    public void restaurarContenidoCuandoFallaLaEliminacion()
            throws IOException {

        RepositorioMemoria repositorio = new RepositorioMemoria();

        ContenidoController controlador =
            new ContenidoController(repositorio);

        Pelicula pelicula = crearPelicula();

        controlador.registrarContenido(pelicula);

        repositorio.activarErrorDeGuardado();

        try {

            controlador.eliminarContenido(pelicula.getId());

            fail("Se esperaba una IOException.");

        } catch (IOException e) {

            assertEquals(
                "Error simulado de escritura.",
                e.getMessage()
            );
        }

        assertEquals(1, controlador.obtenerCantidad());

        assertNotNull(
            controlador.buscarContenido(pelicula.getId())
        );
    }


// Comprueba que un identificador inexistente no provoque eliminaciones.

    @Test
    public void eliminarIdentificadorInexistente()
            throws IOException {

        ContenidoController controlador =
            new ContenidoController(new RepositorioMemoria());

        boolean resultado = controlador.eliminarContenido(999999);

        assertFalse(resultado);
        assertEquals(0, controlador.obtenerCantidad());
    }


// Crea una pelicula para reutilizarla en las pruebas.

    private Pelicula crearPelicula() {

        return new Pelicula(
            "Interestelar",
            169,
            "Ciencia ficcion",
            "Paramount Pictures"
        );
    }


// Repositorio temporal utilizado exclusivamente para probar el controlador sin modificar los CSV reales.

    private static class RepositorioMemoria
            implements ContenidoRepository {

        private List<ContenidoAudiovisual> contenidos =
            new ArrayList<>();

        private boolean errorDeGuardado = false;

        @Override
        public List<ContenidoAudiovisual> cargar() {

            return new ArrayList<>(contenidos);
        }

        @Override
        public void guardar(
                List<ContenidoAudiovisual> nuevosContenidos)
                throws IOException {

            if (errorDeGuardado) {

                throw new IOException(
                    "Error simulado de escritura."
                );
            }

            contenidos = new ArrayList<>(nuevosContenidos);
        }

        public void activarErrorDeGuardado() {
            errorDeGuardado = true;
        }
    }
}
