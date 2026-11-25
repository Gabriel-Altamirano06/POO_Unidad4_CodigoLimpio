
package persistencia;

import java.io.IOException;
import java.util.List;

import modelo.ContenidoAudiovisual;

// Define las operaciones de escritura de contenidos.

public interface ContenidoWriter {

    void guardar(List<ContenidoAudiovisual> contenidos)
            throws IOException;
}
