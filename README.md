
# Sistema de Gestión de Contenido Audiovisual

## 1. Descripción del proyecto

El proyecto consiste en un sistema de gestión de contenidos audiovisuales desarrollado en Java mediante Programación Orientada a Objetos.

Esta versión corresponde a la Unidad 4 y representa una evolución del proyecto hecho anteriormente. Se incorporaron mejoras de código limpio, principios SOLID, arquitectura MVC, manejo de archivos CSV y pruebas unitarias utilizando JUnit.

El sistema permite registrar, consultar, buscar y eliminar diferentes tipos de contenidos audiovisuales, conservando la información mediante archivos externos.

## 2. Objetivos

- Mejorar la organización y mantenibilidad del código.
- Aplicar los cinco principios SOLID.
- Implementar la arquitectura Modelo-Vista-Controlador.
- Incorporar lectura y escritura de archivos CSV.
- Desarrollar pruebas unitarias para comprobar el funcionamiento del sistema.

## 3. Tecnologías utilizadas

- Lenguaje de programación: Java.
- Entorno de desarrollo: Eclipse IDE.
- Plataforma de alojamiento: GitHub.
- Gestión de repositorios: GitHub Desktop.
- Pruebas unitarias: JUnit 4.
- Diagrama de clases: Draw.io.

## 4. Funcionalidades del sistema

El sistema permite:

- Registrar películas.
- Registrar series de televisión.
- Registrar documentales.
- Registrar videos musicales.
- Registrar conferencias científicas.
- Asociar actores a películas y videos musicales.
- Asociar temporadas a series.
- Asociar investigadores a documentales y conferencias.
- Consultar todos los contenidos registrados.
- Buscar contenidos mediante su identificador.
- Eliminar contenidos.
- Guardar y recuperar información mediante archivos CSV.

## 5. Estructura del proyecto

El código se encuentra organizado en los siguientes paquetes:

- **modelo:** contiene las clases que representan los contenidos audiovisuales, actores, temporadas e investigadores.
- **vista:** contiene la interfaz de consola y los métodos de interacción con el usuario.
- **controlador:** coordina las operaciones y conecta la vista con el modelo.
- **persistencia:** contiene las interfaces de almacenamiento y la implementación de lectura y escritura CSV.
- **aplicacion:** contiene la clase principal para ejecutar el sistema.
- **test:** contiene las pruebas unitarias.
- **data:** almacena los archivos CSV.
- **docs:** contiene el diagrama de clases y la documentación del proyecto.

## 6. Manejo de archivos CSV

La persistencia se implementó mediante archivos CSV con codificación UTF-8.

Se utilizan cuatro archivos:

- `contenidos.csv`: información de los contenidos audiovisuales.
- `actores.csv`: información de los actores y sus relaciones.
- `temporadas.csv`: información de las temporadas de las series.
- `investigadores.csv`: información de los investigadores y sus relaciones.

## 7. Arquitectura MVC y principios SOLID

El proyecto utiliza el patrón Modelo-Vista-Controlador:

- **Modelo:** representa los datos y las entidades del sistema.
- **Vista:** presenta los menús y recibe información del usuario.
- **Controlador:** coordina las operaciones y gestiona la comunicación entre componentes.

Se aplican los principios SOLID:

- SRP: separación de responsabilidades.
- OCP: diseño extensible mediante abstracciones y herencia.
- LSP: utilización de las subclases mediante la clase base.
- ISP: separación de las interfaces de lectura y escritura.
- DIP: dependencia de abstracciones mediante inyección de dependencias.

## 8. Ejecución del proyecto

Para ejecutar la aplicación:

1. Clonar el repositorio desde GitHub.
2. Abrir Eclipse IDE.
3. Importar el proyecto o configurarlo como proyecto Java.
4. Comprobar que `src/main/java` esté configurada como carpeta de código fuente.
5. Verificar que JUnit 4 esté agregado al Build Path.
6. Abrir `SistemaAudiovisualApp.java`.
7. Ejecutar mediante `Run As → Java Application`.

## 9. Ejecución de pruebas unitarias

Las pruebas se encuentran en la carpeta `test`.

Se implementaron pruebas para comprobar:

- El almacenamiento y recuperación de contenidos audiovisuales.
- La lectura de directorios sin archivos CSV.
- El manejo de identificadores incorrectos.
- El registro de contenidos.
- La búsqueda mediante identificadores.
- La eliminación de contenidos.
- El tratamiento de valores nulos.
- El control de errores durante el almacenamiento.

## 10. Conclusión

La actualización del sistema permitió mejorar su organización y mantenibilidad mediante la aplicación de principios de programación limpia, SOLID y arquitectura MVC.

La incorporación de persistencia mediante archivos CSV permite conservar la información registrada, mientras que las pruebas unitarias contribuyen a comprobar la calidad y fiabilidad de las funcionalidades implementadas.
