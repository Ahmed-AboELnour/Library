package com.library.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.config.JwtTokenProvider;
import com.library.config.TestSecurityConfig;
import com.library.entity.Author;
import com.library.service.AuthorService;
import com.library.Controller.AuthorController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class AuthorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    private Author author;

    @BeforeEach
    public void setUp() {
        author = new Author();
        author.setId(1L);
        author.setName("Author");
        author.setBiography("Author Biography");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllAuthors() throws Exception {

        when(authorService.getAllAuthors()).thenReturn(Collections.singletonList(author));

        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(author.getId()))
                .andExpect(jsonPath("$[0].name").value(author.getName()))
                .andExpect(jsonPath("$[0].biography").value(author.getBiography()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAuthorById() throws Exception {

        when(authorService.getAuthorById(anyLong())).thenReturn(author);


        mockMvc.perform(get("/authors/{id}", author.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(author.getId()))
                .andExpect(jsonPath("$.name").value(author.getName()))
                .andExpect(jsonPath("$.biography").value(author.getBiography()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateAuthor() throws Exception {

        when(authorService.createAuthor(any(Author.class))).thenReturn(author);


        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(author)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(author.getId()))
                .andExpect(jsonPath("$.name").value(author.getName()))
                .andExpect(jsonPath("$.biography").value(author.getBiography()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateAuthor() throws Exception {

        when(authorService.updateAuthor(anyLong(), any(Author.class))).thenReturn(author);


        mockMvc.perform(put("/authors/{id}", author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(author)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(author.getId()))
                .andExpect(jsonPath("$.name").value(author.getName()))
                .andExpect(jsonPath("$.biography").value(author.getBiography()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteAuthor() throws Exception {

        doNothing().when(authorService).deleteAuthor(anyLong());


        mockMvc.perform(delete("/authors/{id}", author.getId()))
                .andExpect(status().isNoContent());

        verify(authorService, times(1)).deleteAuthor(author.getId());
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
