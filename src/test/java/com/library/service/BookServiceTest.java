package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Author author = new Author("Ahmed", "Biography");
        book = new Book("History Book",  "10", new Date(), true,author);
    }

    @Test
    void testFindBookById() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        Optional<Book> foundBook = Optional.ofNullable(bookService.getBookById(1L));

        assertTrue(foundBook.isPresent());
        assertEquals(book.getTitle(), foundBook.get().getTitle());
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void testSaveBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.createBook(book);

        assertEquals(book.getTitle(), savedBook.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testUpdateAuthor() {
        // Mock an existing author
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("title1");

        // Mock the updated author data
        Book updatedBookData = new Book();
        updatedBookData.setId(1L); // Existing author's ID
        updatedBookData.setTitle("title1");

        // Mock repository behavior
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBookData);

        // Call the service method to update the author
        Book updatedBook = bookService.updateBook(1L, updatedBookData);

        // Verify the repository method was called
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));

        // Assert that the returned author has the updated name
        Assertions.assertEquals("title1", updatedBook.getTitle());
    }

}
