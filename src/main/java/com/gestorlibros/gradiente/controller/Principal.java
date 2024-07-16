package com.gestorlibros.gradiente.controller;

import com.gestorlibros.gradiente.config.AppConfig;
import com.gestorlibros.gradiente.model.Author;
import com.gestorlibros.gradiente.model.Book;
import com.gestorlibros.gradiente.repository.AuthorRepository;
import com.gestorlibros.gradiente.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
@ComponentScan(basePackages = "com.gestorlibros.gradiente")
public class Principal implements CommandLineRunner {

    Scanner teclado = new Scanner(System.in);
    @Autowired
    private AppConfig appConfig;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public static void main(String[] args) {
        SpringApplication.run(Principal.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        muestraMenu();
    }

    private void muestraMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("1 - Buscar Libro por Título (buscar en la API)");
            System.out.println("2 - Listar Libros registrados (buscar en la base de datos)");
            System.out.println("3 - Listar Autores registrados (buscar en la base de datos)");
            System.out.println("4 - Listar Autores vivos en determinado año (buscar en la base de datos)");
            System.out.println("5 - Listar Libros por Idioma (buscar en la base de datos)");
            System.out.println("0 - Salir");
            opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir nueva línea

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo(scanner);
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresEnDeterminadoAnho();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
        scanner.close();
    }
    private void buscarLibroPorTitulo(Scanner scanner) {
        System.out.println("Ingrese el título del libro que desea buscar:");
        String titulo = scanner.nextLine();

        List<Book> libros = appConfig.obtenerDatosDeAPI(titulo);

        if (libros != null && !libros.isEmpty()) {
            Book libro = libros.get(0); // Solo tomamos la primera coincidencia

            Set<Author> autores = new HashSet<>();
            for (Author autor : libro.getAuthors()) {
                Author autorExistente = authorRepository.findByNombre(autor.getNombre());
                if (autorExistente == null) {
                    autorExistente = authorRepository.save(autor);
                }
                autores.add(autorExistente);
            }
            libro.setAuthors(autores);
            bookRepository.save(libro);

            System.out.println("Libro guardado exitosamente.");
            System.out.println(libro);
        } else {
            System.out.println("No se encontraron libros con ese título.");
        }
    }



    private void listarLibros() {
        List<Book> libros = bookRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(libro -> System.out.println("Título: " + libro.getTitulo() + ", Idioma: " + libro.getIdioma() + ", Año: " + libro.getYear()));
        }
    }

    private void listarAutores() {
        List<Author> autores = authorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            autores.forEach(autor -> System.out.println("Nombre: " + autor.getNombre() + ", Año de nacimiento: " + autor.getBirthYear() + ", Año de fallecimiento: " + (autor.getDeathYear() != null ? autor.getDeathYear() : "N/A")));
        }
    }

    private void listarAutoresEnDeterminadoAnho() {
        System.out.println("Ingrese el año:");
        int anho = teclado.nextInt();
        List<Author> autores = authorRepository.findByBirthYearBeforeAndDeathYearAfter(anho, anho);
        if (autores.isEmpty()) {
            System.out.println("No hay autores vivos en el año " + anho);
        } else {
            autores.forEach(autor -> System.out.println("Nombre: " + autor.getNombre() + ", Año de nacimiento: " + autor.getBirthYear() + ", Año de fallecimiento: " + (autor.getDeathYear() != null ? autor.getDeathYear() : "N/A")));
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma:");
        String idioma = teclado.nextLine();
        List<Book> libros = bookRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No hay libros en el idioma " + idioma);
        } else {
            libros.forEach(libro -> System.out.println("Título: " + libro.getTitulo() + ", Idioma: " + libro.getIdioma() + ", Año: " + libro.getYear()));
        }
    }

}
