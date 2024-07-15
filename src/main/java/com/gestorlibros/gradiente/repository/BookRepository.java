package com.gestorlibros.gradiente.repository;

import com.gestorlibros.gradiente.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitle(String title);
}
