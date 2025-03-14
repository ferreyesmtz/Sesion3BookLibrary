package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.service.LibraryService;

@RestController
@RequestMapping("/api/books")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    // Obtener todos los libros
    @GetMapping
    public List<Book> getAllBooks() {
        return libraryService.getAllBooks();
    }

    // Obtener un libro por su id
    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable Long id) {
        return libraryService.getBookById(id);
    }

    // Agregar un libro
    @PostMapping
    public Book addBook(@RequestBody Book book) {
        return libraryService.addBook(book);
    }

    // Actualizar un libro
    @PutMapping("/{id}")
    public Optional<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return libraryService.updateBook(id, updatedBook);
    }

    // Eliminar un libro
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        boolean isRemoved = libraryService.deleteBook(id);
        if (isRemoved) {
            return "Book deleted successfully";
        } else {
            return "Book not found";
        }
    }

    // Buscar libros por autor
    @GetMapping("/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return libraryService.getBooksByAuthor(author);
    }

    // Buscar libros por título
    @GetMapping("/title/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        return libraryService.getBooksByTitle(title);
    }

    

    // Verificar si un libro existe
    @GetMapping("/exists/{id}")
    public boolean bookExists(@PathVariable Long id) {
        return libraryService.bookExists(id);
    }


}