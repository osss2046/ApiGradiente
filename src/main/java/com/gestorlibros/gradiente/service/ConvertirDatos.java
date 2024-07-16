package com.gestorlibros.gradiente.service;
import com.gestorlibros.gradiente.model.Book;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

public class ConvertirDatos {

    private final Gson gson;

    public ConvertirDatos() {
        this.gson = new Gson();
    }

    public Book deserializar(String json) {
        return gson.fromJson(json, Book.class);
    }
}