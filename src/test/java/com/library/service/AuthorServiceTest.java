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

        when(authorRepository.findAll()).thenReturn(Arrays.asList(author));
        List<Author> authors = authorService.getAllAuthors();

        assertNotNull(authors);
        assertEquals(1, authors.size());
        assertEquals(author.getName(), authors.get(0).getName());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    public void testGetAuthorById() {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        Author foundAuthor = authorService.getAuthorById(author.getId());

        assertNotNull(foundAuthor);
        assertEquals(author.getName(), foundAuthor.getName());
        verify(authorRepository, times(1)).findById(author.getId());
    }

    @Test
    public void testCreateAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author createdAuthor = authorService.createAuthor(author);

        assertNotNull(createdAuthor);
        assertEquals(author.getName(), createdAuthor.getName());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    public void testUpdateAuthor() {
        Author updatedAuthor = new Author();
        updatedAuthor.setName("Updated Name");
        updatedAuthor.setBiography("Updated Biography");

        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(updatedAuthor);

        Author result = authorService.updateAuthor(author.getId(), updatedAuthor);

        assertNotNull(result);
        assertEquals(updatedAuthor.getName(), result.getName());
        assertEquals(updatedAuthor.getBiography(), result.getBiography());
        verify(authorRepository, times(1)).findById(author.getId());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    public void testDeleteAuthor() {
        authorService.deleteAuthor(author.getId());

        verify(authorRepository, times(1)).deleteById(author.getId());
    }
}
