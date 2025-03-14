package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String genre;
    private String published_date;
    private String isbn;
    private int stock;
    private boolean available;

    // Constructor vacío necesario para JPA
    public Book() {}

    public Book(String title, String author, String genre, String published_date, String isbn, int stock) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.published_date = published_date;
        this.isbn = isbn;
        this.stock = stock;
        this.available = stock > 0;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublishedDate() {
        return published_date;
    }

    public void setPublishedDate(String published_date) {
        this.published_date = published_date;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(int stock) {
        this.available = stock > 0;
    }
   
    
}
