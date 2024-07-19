package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        author = new Author("Ahmed", "Biography");
    }

    @Test
    void testFindAuthorById() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));

        Optional<Author> foundAuthor = Optional.ofNullable(authorService.getAuthorById(1L));

        assertTrue(foundAuthor.isPresent());
        assertEquals(author.getName(), foundAuthor.get().getName());
        verify(authorRepository, times(1)).findById(anyLong());
    }

    @Test
    void testSaveAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author savedAuthor = authorService.createAuthor(author);

        assertEquals(author.getName(), savedAuthor.getName());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void testUpdateAuthor() {
        // Mock an existing author
        Author existingAuthor = new Author();
        existingAuthor.setId(1L);
        existingAuthor.setName("Mohamed");

        // Mock the updated author data
        Author updatedAuthorData = new Author();
        updatedAuthorData.setId(1L); // Existing author's ID
        updatedAuthorData.setName("Mohamed");

        // Mock repository behavior
        when(authorRepository.findById(1L)).thenReturn(java.util.Optional.of(existingAuthor));
        when(authorRepository.save(any(Author.class))).thenReturn(updatedAuthorData);

        // Call the service method to update the author
        Author updatedAuthor = authorService.updateAuthor(1L, updatedAuthorData);

        // Verify the repository method was called
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).save(any(Author.class));

        // Assert that the returned author has the updated name
        Assertions.assertEquals("Mohamed", updatedAuthor.getName());
    }


}
