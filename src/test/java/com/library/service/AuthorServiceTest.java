package com.library.service;

import com.library.Controller.AuthorController;
import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;


import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import java.util.Arrays;
import java.util.List;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    public void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Author Name");
        author.setBiography("Author Biography");
    }

    @Test
    public void testGetAllAuthors() {
        // Arrange
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author));

        // Act
        List<Author> authors = authorService.getAllAuthors();

        // Assert
        assertNotNull(authors);
        assertEquals(1, authors.size());
        assertEquals(author.getName(), authors.get(0).getName());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    public void testGetAuthorById() {
        // Arrange
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

        // Act
        Author foundAuthor = authorService.getAuthorById(author.getId());

        // Assert
        assertNotNull(foundAuthor);
        assertEquals(author.getName(), foundAuthor.getName());
        verify(authorRepository, times(1)).findById(author.getId());
    }

    @Test
    public void testCreateAuthor() {
        // Arrange
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        // Act
        Author createdAuthor = authorService.createAuthor(author);

        // Assert
        assertNotNull(createdAuthor);
        assertEquals(author.getName(), createdAuthor.getName());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    public void testUpdateAuthor() {
        // Arrange
        Author updatedAuthor = new Author();
        updatedAuthor.setName("Updated Name");
        updatedAuthor.setBiography("Updated Biography");

        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(updatedAuthor);

        // Act
        Author result = authorService.updateAuthor(author.getId(), updatedAuthor);

        // Assert
        assertNotNull(result);
        assertEquals(updatedAuthor.getName(), result.getName());
        assertEquals(updatedAuthor.getBiography(), result.getBiography());
        verify(authorRepository, times(1)).findById(author.getId());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    public void testDeleteAuthor() {
        // Act
        authorService.deleteAuthor(author.getId());

        // Assert
        verify(authorRepository, times(1)).deleteById(author.getId());
    }
}
