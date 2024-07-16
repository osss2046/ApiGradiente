package com.gestorlibros.gradiente.repository;

import com.gestorlibros.gradiente.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByNombre(String nombre);

    List<Author> findByBirthYearBeforeAndDeathYearAfter(int birthYear, int deathYear);
}
