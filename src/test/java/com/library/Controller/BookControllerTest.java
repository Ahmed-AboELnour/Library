package com.library.Controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    private Book book;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        book = new Book();
        book.setId(1L);
        book.setTitle("Mockito Testing");
        book.setAuthor(new Author("Ahmed", "Biography"));
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = Arrays.asList(book);
        when(bookService.getAllBooks()).thenReturn(books);

        List<Book> result = bookController.getAllBooks();

        assertEquals(1, result.size());
        assertEquals(book.getTitle(), result.get(0).getTitle());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void testGetBookById() {
        when(bookService.getBookById(1L)).thenReturn(book);

        Book result = bookController.getBookById(1L);

        assertEquals(book.getTitle(), result.getTitle());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    public void testCreateBook() {
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        Book result = bookController.createBook(book);

        assertEquals(book.getTitle(), result.getTitle());
        verify(bookService, times(1)).createBook(any(Book.class));
    }

    @Test
    public void testUpdateBook() {
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(book);

        Book result = bookController.updateBook(1L, book);

        assertEquals(book.getTitle(), result.getTitle());
        verify(bookService, times(1)).updateBook(eq(1L), any(Book.class));
    }

    @Test
    public void testDeleteBook() {
        doNothing().when(bookService).deleteBook(1L);

        ResponseEntity<String> response = bookController.deleteBook(1L);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(bookService, times(1)).deleteBook(1L);
    }

    @Test
    public void testSearchBooks() {
        List<Book> books = Arrays.asList(book);
        when(bookService.searchBooks("Mockito Testing", "Author Name")).thenReturn(books);

        List<Book> result = bookController.searchBooks("Mockito Testing", "Author Name");

        assertEquals(1, result.size());
        assertEquals(book.getTitle(), result.get(0).getTitle());
        verify(bookService, times(1)).searchBooks("Mockito Testing", "Author Name");
    }
}