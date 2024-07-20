package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.Loan;
import com.library.exception.CustomIllegalArgumentException;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LoanService loanService;

    private Loan loan;
    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setAvailable(true);

        loan = new Loan();
        loan.setId(1L);
        loan.setBook(book);
        loan.setBorrower("Borrower Name");
        loan.setLoanDate(new Date());
        loan.setReturnDate(new Date());
    }

    @Test
    public void testGetAllLoans() {
        // Arrange
        when(loanRepository.findAll()).thenReturn(Arrays.asList(loan));

        // Act
        List<Loan> loans = loanService.getAllLoans();

        // Assert
        assertNotNull(loans);
        assertEquals(1, loans.size());
        assertEquals(loan.getBorrower(), loans.get(0).getBorrower());
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    public void testGetLoanById() {
        // Arrange
        when(loanRepository.findById(loan.getId())).thenReturn(Optional.of(loan));

        // Act
        Loan foundLoan = loanService.getLoanById(loan.getId());

        // Assert
        assertNotNull(foundLoan);
        assertEquals(loan.getBorrower(), foundLoan.getBorrower());
        verify(loanRepository, times(1)).findById(loan.getId());
    }

    @Test
    public void testGetLoanByIdThrowsExceptionForInvalidId() {
        // Act & Assert
        CustomIllegalArgumentException exception = assertThrows(CustomIllegalArgumentException.class, () -> {
            loanService.getLoanById(-1L);
        });

        assertEquals("Invalid loan ID: -1", exception.getMessage());
    }

    @Test
    public void testCreateLoan() {
        // Arrange
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        // Act
        Loan createdLoan = loanService.createLoan(loan);

        // Assert
        assertNotNull(createdLoan);
        assertEquals(loan.getBorrower(), createdLoan.getBorrower());
        verify(bookRepository, times(1)).findById(book.getId());
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    public void testCreateLoanThrowsExceptionForNonExistentBook() {
        // Arrange
        when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(loan);
        });

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    public void testUpdateLoan() {
        // Arrange
        Loan updatedLoan = new Loan();
        updatedLoan.setBook(book);
        updatedLoan.setBorrower("Updated Borrower");
        updatedLoan.setLoanDate(new Date());
        updatedLoan.setReturnDate(new Date());

        when(loanRepository.findById(loan.getId())).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(updatedLoan);

        // Act
        Loan result = loanService.updateLoan(loan.getId(), updatedLoan);

        // Assert
        assertNotNull(result);
        assertEquals(updatedLoan.getBorrower(), result.getBorrower());
        verify(loanRepository, times(1)).findById(loan.getId());
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    public void testDeleteLoan() {
        // Act
        loanService.deleteLoan(loan.getId());

        // Assert
        verify(loanRepository, times(1)).deleteById(loan.getId());
    }

    @Test
    public void testGetOverdueLoans() {
        // Arrange
        Date currentDate = new Date();
        when(loanRepository.findByReturnDateBeforeAndBookAvailable(any(Date.class), eq(true)))
                .thenReturn(Arrays.asList(loan));

        // Act
        List<Loan> overdueLoans = loanService.getOverdueLoans(currentDate);

        // Assert
        assertNotNull(overdueLoans);
        assertEquals(1, overdueLoans.size());
        assertEquals(loan.getBorrower(), overdueLoans.get(0).getBorrower());
        verify(loanRepository, times(1))
                .findByReturnDateBeforeAndBookAvailable(any(Date.class), eq(true));
    }
}
