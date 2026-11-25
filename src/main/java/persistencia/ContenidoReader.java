
package persistencia;

import java.io.IOException;
import java.util.List;

import modelo.ContenidoAudiovisual;


// Define las operaciones de lectura de contenidos.

public interface ContenidoReader {

    List<ContenidoAudiovisual> cargar() throws IOException;
}
