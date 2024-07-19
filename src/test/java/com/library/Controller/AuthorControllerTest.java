package com.library.Controller;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import com.library.entity.Author;
import com.library.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;


public class AuthorControllerTest {

    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        author = new Author();
        author.setId(1L);
        author.setName("Author Name");
        author.setBiography("Author Biography");
    }

    @Test
    public void testGetAllAuthors() {
        List<Author> authors = Arrays.asList(author);
        when(authorService.getAllAuthors()).thenReturn(authors);

        List<Author> result = authorController.getAllAuthors();

        assertEquals(1, result.size());
        assertEquals(author.getName(), result.get(0).getName());
        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    public void testGetAuthorById() {
        when(authorService.getAuthorById(1L)).thenReturn(author);

        Author result = authorController.getAuthorById(1L);

        assertEquals(author.getName(), result.getName());
        verify(authorService, times(1)).getAuthorById(1L);
    }

    @Test
    public void testCreateAuthor() {
        when(authorService.createAuthor(any(Author.class))).thenReturn(author);

        Author result = authorController.createAuthor(author);

        assertEquals(author.getName(), result.getName());
        verify(authorService, times(1)).createAuthor(any(Author.class));
    }

    @Test
    public void testUpdateAuthor() {
        when(authorService.updateAuthor(eq(1L), any(Author.class))).thenReturn(author);

        Author result = authorController.updateAuthor(1L, author);

        assertEquals(author.getName(), result.getName());
        verify(authorService, times(1)).updateAuthor(eq(1L), any(Author.class));
    }

    @Test
    public void testDeleteAuthor() {
        doNothing().when(authorService).deleteAuthor(1L);

        ResponseEntity<Void> response = authorController.deleteAuthor(1L);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(authorService, times(1)).deleteAuthor(1L);
    }
}