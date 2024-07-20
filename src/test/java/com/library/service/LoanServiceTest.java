package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.Loan;
import com.library.repository.LoanRepository;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    private Loan loan;
    private Book book;
    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Date dt = new Date();
        DateTime dtOrg = new DateTime(dt);
        DateTime dtPlusOne = dtOrg.plusDays(1);
       // author = new Author("Ahmed", "Biography");
        book = new Book(1L,"History Book", "10", new Date(), true);
        loan = new Loan(book, "Borrower", new Date(), dtPlusOne.toDate());
    }

    @Test
    void testFindLoanById() {
        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loan));

        Optional<Loan> foundLoan = Optional.ofNullable(loanService.getLoanById(1L));

        assertTrue(foundLoan.isPresent());
        assertEquals(loan.getBorrower(), foundLoan.get().getBorrower());
        verify(loanRepository, times(1)).findById(anyLong());
    }

//    @Test
//    void testSaveLoan() {
//        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
//
//        Loan savedLoan = loanService.createLoan(loan);
//
//        assertEquals(loan.getLoanDate(), savedLoan.getId());
//        verify(loanRepository, times(1)).save(any(Loan.class));
//    }

}
