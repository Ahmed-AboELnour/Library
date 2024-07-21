package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Author author;

    @BeforeEach
    public void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Author Name");
        author.setBiography("Author Biography");

        book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setAuthor(author);
        book.setIsbn("1234567890");
        book.setPublishedDate(new Date());
        book.setAvailable(true);
    }

    @Test
    public void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        List<Book> books = bookService.getAllBooks();

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(book.getTitle(), books.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testGetBookById() {
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        Book foundBook = bookService.getBookById(book.getId());

        assertNotNull(foundBook);
        assertEquals(book.getTitle(), foundBook.getTitle());
        verify(bookRepository, times(1)).findById(book.getId());
    }

    @Test
    public void testCreateBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book createdBook = bookService.createBook(book);
        assertNotNull(createdBook);
        assertEquals(book.getTitle(), createdBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testUpdateBook() {
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor(author);
        updatedBook.setIsbn("0987654321");
        updatedBook.setPublishedDate(new Date());
        updatedBook.setAvailable(false);

        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book result = bookService.updateBook(book.getId(), updatedBook);

        assertNotNull(result);
        assertEquals(updatedBook.getTitle(), result.getTitle());
        assertEquals(updatedBook.getIsbn(), result.getIsbn());
        assertEquals(updatedBook.getPublishedDate(), result.getPublishedDate());
        assertFalse(result.isAvailable());
        verify(authorRepository, times(1)).findById(author.getId());
        verify(bookRepository, times(1)).findById(book.getId());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testDeleteBook() {
        bookService.deleteBook(book.getId());
        verify(bookRepository, times(1)).deleteById(book.getId());
    }

    @Test
    public void testDeleteBookThrowsException() {
        doThrow(new DataIntegrityViolationException("Cannot delete this book as it is referenced by other records."))
                .when(bookRepository).deleteById(book.getId());
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            bookService.deleteBook(book.getId());
        });

        assertEquals("Cannot delete this book as it is referenced by other records.", exception.getMessage());
        verify(bookRepository, times(1)).deleteById(book.getId());
    }

    @Test
    public void testSearchBooks() {
        when(bookRepository.findByTitleContainingAndAuthorNameContaining("Book", "Author"))
                .thenReturn(Arrays.asList(book));

        List<Book> books = bookService.searchBooks("Book", "Author");

        // Assert
        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(book.getTitle(), books.get(0).getTitle());
        verify(bookRepository, times(1))
                .findByTitleContainingAndAuthorNameContaining("Book", "Author");
    }
}
