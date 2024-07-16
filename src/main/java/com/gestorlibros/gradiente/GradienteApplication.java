package com.gestorlibros.gradiente;

import com.gestorlibros.gradiente.controller.Principal;
import com.gestorlibros.gradiente.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GradienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradienteApplication.class, args);
	}
}
