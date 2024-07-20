package com.library.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.config.TestSecurityConfig;
import com.library.entity.Author;
import com.library.entity.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.is;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private Book book;

    private Author author;

    @BeforeEach
    public void setUp() {
        author = new Author();
        author.setName("Author2");
        author.setBiography("Author2 Biography");

        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor(author);
        book.setPublishedDate(new Date());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    public void testGetAllBooks() throws Exception {
        // Arrange
        List<Book> books = Arrays.asList(book);
        when(bookService.getAllBooks()).thenReturn(books);

        // Act & Assert
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(book.getId().intValue())))
                .andExpect(jsonPath("$[0].title", is(book.getTitle())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetBookById() throws Exception {
        // Arrange
        when(bookService.getBookById(anyLong())).thenReturn(book);

        // Act & Assert
        mockMvc.perform(get("/books/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(book.getId().intValue())))
                .andExpect(jsonPath("$.title", is(book.getTitle())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateBook() throws Exception {
        // Arrange
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        // Act & Assert
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(book.getId().intValue())))
                .andExpect(jsonPath("$.title", is(book.getTitle())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateBook() throws Exception {
        // Arrange
        when(bookService.updateBook(anyLong(), any(Book.class))).thenReturn(book);

        // Act & Assert
        mockMvc.perform(put("/books/{id}", book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(book)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(book.getId().intValue())))
                .andExpect(jsonPath("$.title", is(book.getTitle())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteBook() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/books/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Book deleted successfully"));

        // Verify that the delete method was called
        verify(bookService).deleteBook(book.getId());
    }

    private String asJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
