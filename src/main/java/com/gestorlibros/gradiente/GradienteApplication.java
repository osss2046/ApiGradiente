package com.gestorlibros.gradiente;

import com.gestorlibros.gradiente.model.Book;
import com.gestorlibros.gradiente.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Scanner;


@SpringBootApplication
@ComponentScan(basePackages = "com.gestorlibros.gradiente")
public class GradienteApplication implements CommandLineRunner {

	private final BookService bookService;

	@Autowired
	public GradienteApplication(BookService bookService) {
		this.bookService = bookService;
	}

	public static void main(String[] args) {
		SpringApplication.run(GradienteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		while (!exit) {
			System.out.println("Bienvenido al sistema de gestión de libros");
			System.out.println("Seleccione una opción:");
			System.out.println("1. Mostrar todos los libros");
			System.out.println("2. Buscar libro por ID");
			System.out.println("3. Buscar libros por título o autor");
			System.out.println("4. Salir");

			String input = scanner.nextLine(); // Leer la entrada del usuario como cadena

			switch (input) {
				case "1":
					displayAllBooks();
					break;
				case "2":
					searchBookById(scanner);
					break;
				case "3":
					searchBooksByQuery(scanner);
					break;
				case "4":
					exit = true;
					System.out.println("Saliendo del sistema...");
					break;
				default:
					System.out.println("Opción inválida. Por favor seleccione una opción válida.");
			}
		}

		scanner.close();
	}

	private void displayAllBooks() {
		List<Book> books = bookService.findAllBooks();
		if (books != null && !books.isEmpty()) {
			System.out.println("Listado de libros disponibles:");
			for (Book book : books) {
				System.out.println(book.getId() + ": " + book.getTitle() + " - " + book.getAuthor());
			}
		} else {
			System.out.println("No se encontraron libros.");
		}
	}

	private void searchBookById(Scanner scanner) {
		System.out.println("Ingrese el ID del libro:");
		long id = scanner.nextLong();
		Book book = bookService.findBookById(id);
		if (book != null) {
			System.out.println("Libro encontrado:");
			System.out.println("ID: " + book.getId());
			System.out.println("Título: " + book.getTitle());
			System.out.println("Autor: " + book.getAuthor());
		} else {
			System.out.println("No se encontró ningún libro con el ID proporcionado.");
		}
	}

	private void searchBooksByQuery(Scanner scanner) {
		System.out.println("Ingrese el título o autor a buscar:");
		String query = scanner.nextLine();
		List<Book> books = bookService.searchBooks(query);
		if (books != null && !books.isEmpty()) {
			System.out.println("Resultados de la búsqueda:");
			for (Book book : books) {
				System.out.println(book.getId() + ": " + book.getTitle() + " - " + book.getAuthor());
			}
		} else {
			System.out.println("No se encontraron libros que coincidan con la búsqueda.");
		}
	}
}