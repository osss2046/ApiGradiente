package com.gestorlibros.gradiente.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private int year;

    @ManyToMany(mappedBy = "books")
    private Set<Author> authors;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Título: ").append(titulo).append("\n")
                .append("Idioma: ").append(idioma).append("\n")
                .append("Año: ").append(year).append("\n")
                .append("Autores: ");
        for (Author author : authors) {
            sb.append(author.getNombre()).append(" (").append(author.getBirthYear());
            if (author.getDeathYear() != null) {
                sb.append(" - ").append(author.getDeathYear());
            }
            sb.append("), ");
        }
        // Remove the last comma and space
        if (!authors.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}