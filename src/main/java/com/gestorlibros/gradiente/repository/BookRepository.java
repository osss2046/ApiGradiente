package com.gestorlibros.gradiente.repository;

import com.gestorlibros.gradiente.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByIdioma(String idioma);
}
