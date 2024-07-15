package com.gestorlibros.gradiente.service;

import com.gestorlibros.gradiente.model.Book;
import com.gestorlibros.gradiente.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;


@Service
public class BookService {

    private final String BASE_URL = "https://gutendex.com/books";
    private final RestTemplate restTemplate;

    @Autowired
    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Book> findAllBooks() {
        String url = BASE_URL;
        GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);
        return response != null ? Arrays.asList(response.getResults()) : null;
    }

    public Book findBookById(long id) {
        String url = BASE_URL + "/" + id;
        return restTemplate.getForObject(url, Book.class);
    }

    public List<Book> searchBooks(String searchQuery) {
        String url = UriComponentsBuilder.fromUriString(BASE_URL)
                .queryParam("search", searchQuery)
                .build().toUriString();
        GutendexResponse response = restTemplate.getForObject(url, GutendexResponse.class);
        return response != null ? Arrays.asList(response.getResults()) : null;
    }
}