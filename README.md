# Proyecto Gestor de Libros
Aplicación de consola en Java para buscar libros mediante la API de Gutendex y almacenarlos en una base de datos.

## Descripción
- Principal: Controlador principal que interactúa con el usuario.
- AppConfig: Conecta a la API de Gutendex y obtiene datos.
- Book y Author: Modelos para libros y autores.
- BookRepository y AuthorRepository: Repositorios para persistencia de datos.
## Flujo del Proyecto
- Inicio: Principal arranca la aplicación con Spring Boot.
- Menú: El usuario elige opciones en la consola.
- Búsqueda en API: AppConfig busca libros en la API según el título ingresado.
- Deserialización: La respuesta de la API se convierte en objetos Book y Author.
- Persistencia: Se guardan los libros y autores en la base de datos.
- Resultado: Se muestra la información del libro en la consola.
