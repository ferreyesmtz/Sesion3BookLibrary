package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Book;

@Service
public class LibraryService {

    // Lista simulada de libros en memoria
    private List<Book> books = new ArrayList<>();

    // Agregar un libro
    public Book addBook(Book book) {
        books.add(book);
        return book;
    }

    // Obtener todos los libros
    public List<Book> getAllBooks() {
        return books;
    }

    // Obtener un libro por su id
    public Optional<Book> getBookById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    // Actualizar un libro
    public Optional<Book> updateBook(Long id, Book updatedBook) {
        Optional<Book> bookOpt = getBookById(id);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setIsbn(updatedBook.getIsbn());
            book.setStock(updatedBook.getStock());
            return Optional.of(book);
        }
        return Optional.empty();
    }

    // Eliminar un libro
    public boolean deleteBook(Long id) {
        return books.removeIf(book -> book.getId().equals(id));
    }

    // Buscar libros por autor
    public List<Book> getBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }
        return result;
    }

    // Buscar libros por título
    public List<Book> getBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                result.add(book);
            }
        }
        return result;
    }

    
    // Verificar si un libro existe
    public boolean bookExists(Long id) {
        return books.stream().anyMatch(book -> book.getId().equals(id));
    }

    // Obtener el número total de copias de un libro
    public int getBookStock(Long id) {
        Optional<Book> bookOpt = getBookById(id);
        if (bookOpt.isPresent()) {
            return bookOpt.get().getAvailableStock();
        }
        return 0;
    }
    

    // Limpiar todos los libros
    public void clearAllBooks() {
        books.clear();
    }
}
