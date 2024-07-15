package com.gestorlibros.gradiente.service;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gestorlibros.gradiente.model.Book;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GutendexResponse {

    private int count;
    private String next;
    private String previous;
    private Book[] results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public Book[] getResults() {
        return results;
    }

    public void setResults(Book[] results) {
        this.results = results;
    }
}
