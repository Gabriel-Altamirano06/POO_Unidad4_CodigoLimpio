
package vista;

import java.util.List;
import java.util.Scanner;

import modelo.ContenidoAudiovisual;


 //Presenta los menus y resultados del sistema audiovisual.
 
public class ContenidoVista {

    private final Scanner entrada;

    public ContenidoVista() {
        entrada = new Scanner(System.in);
    }

    
     // Muestra las opciones principales del sistema.
     
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

    
     // Lee la opcion seleccionada por el usuario.
     
    public int leerOpcion() {

        System.out.print("Seleccione una opcion: ");

        String texto = entrada.nextLine();

        try {
            return Integer.parseInt(texto.trim());

        } catch (NumberFormatException e) {
            return -1;
        }
    }

    
     // Muestra un mensaje en consola.
     
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    
     // Muestra todos los contenidos registrados.
    
    public void mostrarContenidos(
            List<ContenidoAudiovisual> contenidos) {

        System.out.println("\n--- CONTENIDOS AUDIOVISUALES ---");

        if (contenidos == null || contenidos.isEmpty()) {
            mostrarMensaje("No existen contenidos registrados.");
            return;
        }

        for (ContenidoAudiovisual contenido : contenidos) {

            contenido.mostrarDetalles();

            System.out.println("----------------------------------------");
        }
    }

    
     // Solicita un identificador al usuario.
     
    public int leerIdentificador() {

        System.out.print("Ingrese el ID del contenido: ");

        String texto = entrada.nextLine();

        try {
            return Integer.parseInt(texto.trim());

        } catch (NumberFormatException e) {
            mostrarMensaje("Debe ingresar un numero entero valido.");
            return -1;
        }
    }

    
    // Solicita un texto no vacio.
     
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
    

/**
 * Lee un numero entero y controla los datos incorrectos.
 */
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

/**
 * Lee un numero entero positivo.
 */
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


// Lee una cantidad que puede ser cero, pero no negativa.

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
