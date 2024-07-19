package com.library.Controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.library.entity.Book;
import com.library.entity.Loan;
import com.library.repository.LoanRepository;
import com.library.service.BookService;
import com.library.service.LoanService;
import org.junit.Before;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoanRepository loanRepository;

    @Mock
    private LoanService loanService;
    @Mock
    private BookService bookService;

    @InjectMocks
    private LoanController loanController;

    private static final Long LOAN_ID = 1L; // Static ID for testing

     Loan UPDATED_LOAN = new Loan();



    @Before
    public void setUp() throws Exception {
        // Clear the database
        loanRepository.deleteAll();
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
        Book book = new Book(1L, "The Great Book", "978-3-16-148410-0", new Date(), true);
        bookService.createBook(book);
        UPDATED_LOAN = new Loan(LOAN_ID, "Updated Borrower",book,new Date(), new Date());
    }

    @Test
    @Transactional
    public void testGetAllLoans() throws Exception {
        mockMvc.perform(get("/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testGetLoanById() throws Exception {
        mockMvc.perform(get("/loans/{id}", 1L))
                .andExpect(status().isOk());
    }


@Test
public void testDeleteLoan() throws Exception {
    // Mocking the behavior of the service layer
    doNothing().when(loanService).deleteLoan(LOAN_ID);

    // Perform the DELETE request
    mockMvc.perform(delete("/loans/{id}", LOAN_ID)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent()); // Expect 204 No Content

    // Optionally, verify that the loan was indeed deleted
    mockMvc.perform(get("/loans/{id}", LOAN_ID)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()); // Expect 404 Not Found (if you handle it this way)
}

}
