package com.gestorlibros.gradiente.config;

import com.gestorlibros.gradiente.model.Author;
import com.gestorlibros.gradiente.model.Book;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class AppConfig {
    private static final String API_URL = "https://gutendex.com/books/";

    public List<Book> obtenerDatosDeAPI(String parametroBusqueda) {
        try {
            URL url = new URL(API_URL + "?search=" + URLEncoder.encode(parametroBusqueda, StandardCharsets.UTF_8));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Error al obtener datos de la API. Código de respuesta: " + responseCode);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(content.toString(), JsonObject.class);
            JsonArray resultsArray = jsonObject.getAsJsonArray("results");

            List<Book> books = new ArrayList<>();
            for (JsonElement jsonElement : resultsArray) {
                JsonObject bookJson = jsonElement.getAsJsonObject();
                Book book = new Book();
                book.setTitulo(bookJson.get("title").getAsString());
                book.setIdioma(bookJson.getAsJsonArray("languages").get(0).getAsString());
                // Check if year is present and not null
                if (bookJson.has("year") && !bookJson.get("year").isJsonNull()) {
                    book.setYear(bookJson.get("year").getAsInt());
                }

                Set<Author> authors = new HashSet<>();
                JsonArray authorsArray = bookJson.getAsJsonArray("authors");
                for (JsonElement authorElement : authorsArray) {
                    JsonObject authorJson = authorElement.getAsJsonObject();
                    Author author = new Author();
                    author.setNombre(authorJson.get("name").getAsString());
                    if (authorJson.has("birth_year") && !authorJson.get("birth_year").isJsonNull()) {
                        author.setBirthYear(authorJson.get("birth_year").getAsInt());
                    }
                    if (authorJson.has("death_year") && !authorJson.get("death_year").isJsonNull()) {
                        author.setDeathYear(authorJson.get("death_year").getAsInt());
                    }
                    authors.add(author);
                }
                book.setAuthors(authors);
                books.add(book);
            }
            return books;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
