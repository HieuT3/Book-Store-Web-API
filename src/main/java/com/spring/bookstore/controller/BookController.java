package com.spring.bookstore.controller;

import com.spring.bookstore.dto.BookDto;
import com.spring.bookstore.entity.Book;
import com.spring.bookstore.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/book")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @GetMapping("{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") int bookId) {
        try {
            Book book =this.bookService.getBookById(bookId);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(this.bookService.getAllBooks());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createBook(@ModelAttribute BookDto bookDto) throws IOException{
        try {
            Book createdBook = this.bookService.createBook(bookDto);
            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<?> UpdateBook(@PathVariable("id") int bookId,
                                        @ModelAttribute BookDto bookDto) throws IOException{
        try {
            Book updatedBook = this.bookService.updateBook(bookId, bookDto);
            return ResponseEntity.ok(updatedBook);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") int bookId) {
        try {
            this.bookService.deleteBook(bookId);
            return ResponseEntity.ok("The book with id" + bookId + " has been deleted successfully!");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("category/{id}")
    public ResponseEntity<?> getAllBooksByCategory(@PathVariable("id") int categoryId) {
        try {
            List<Book> books = this.bookService.getAllBooksByCategory(categoryId);
            return ResponseEntity.ok(books);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
