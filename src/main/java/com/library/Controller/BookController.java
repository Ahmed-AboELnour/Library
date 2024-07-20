package com.library.Controller;

import com.library.entity.Book;
import com.library.service.BookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    @ApiOperation(value = "View a list of available books", response = List.class)
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a book by ID")
    public Book getBookById(@RequestParam("id") Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    @ApiOperation(value = "Add a new book")
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
        bookService.deleteBook(id);
            return ResponseEntity.ok("Book deleted successfully");
        } catch (DataIntegrityViolationException ex) {
            // Handle specific exception and return the correct status
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete this book as it is referenced by other records.");
        } catch (Exception ex) {
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author) {
        return bookService.searchBooks(title, author);
    }
}
