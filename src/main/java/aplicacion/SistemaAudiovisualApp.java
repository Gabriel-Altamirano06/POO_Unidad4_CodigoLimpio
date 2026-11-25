
package aplicacion;

import java.io.IOException;

import controlador.SistemaAudiovisualController;


//Punto de entrada de la aplicación audiovisual.

public class SistemaAudiovisualApp {

    public static void main(String[] args) {

        try {

            SistemaAudiovisualController controlador =
                new SistemaAudiovisualController();

            controlador.iniciar();

        } catch (IOException e) {

            System.out.println(
                "No fue posible cargar los archivos del sistema."
            );

            System.out.println(
                "Detalle del error: " + e.getMessage()
            );
        }
    }
}
