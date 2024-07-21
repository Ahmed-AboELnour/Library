package com.library.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.config.TestSecurityConfig;
import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.Loan;
import com.library.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class LoanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    private Loan loan;
    private Book book;

    private Author author;

    @BeforeEach
    public void setUp() {
        author = new Author();
        author.setName("Author1");
        author.setBiography("Author1 Biography");

        book = new Book();
        book.setTitle("Test Book");
        book.setAuthor(author);
        book.setPublishedDate(new Date());

        loan = new Loan();
        loan.setId(1L);
        loan.setBook(book);
        loan.setBorrower("Tester");
        loan.setLoanDate(new Date());
        loan.setReturnDate(new Date(System.currentTimeMillis() + 1000000)); // Some future date
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllLoans() throws Exception {
        List<Loan> loans = Arrays.asList(loan);
        when(loanService.getAllLoans()).thenReturn(loans);

        mockMvc.perform(get("/loans"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(loan.getId().intValue())))
                .andExpect(jsonPath("$[0].borrower", is(loan.getBorrower())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetLoanById() throws Exception {
        when(loanService.getLoanById(anyLong())).thenReturn(loan);

        mockMvc.perform(get("/loans/{id}", loan.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(loan.getId().intValue())))
                .andExpect(jsonPath("$.borrower", is(loan.getBorrower())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateLoan() throws Exception {

        when(loanService.createLoan(any(Loan.class))).thenReturn(loan);

        mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loan)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(loan.getId().intValue())))
                .andExpect(jsonPath("$.borrower", is(loan.getBorrower())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testUpdateLoan() throws Exception {
        when(loanService.updateLoan(anyLong(), any(Loan.class))).thenReturn(loan);

        mockMvc.perform(put("/loans/{id}", loan.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loan)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(loan.getId().intValue())))
                .andExpect(jsonPath("$.borrower", is(loan.getBorrower())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteLoan() throws Exception {

        mockMvc.perform(delete("/loans/{id}", loan.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Loan with ID " + loan.getId() + " has been successfully deleted."));
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

