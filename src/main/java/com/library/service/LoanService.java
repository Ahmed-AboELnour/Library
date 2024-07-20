package com.library.service;

import com.library.exception.CustomIllegalArgumentException;
import com.library.exception.ResourceNotFoundException;
import com.library.entity.Book;
import com.library.entity.Loan;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan getLoanById(Long id) {
        if (id == null || id <= 0) {
            throw new CustomIllegalArgumentException("Invalid loan ID: " + id);
        }
        return loanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
    }

    public Loan createLoan(Loan loan) {
        Book book = bookRepository.findById(loan.getBook().getId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        loan.setBook(book);
        return loanRepository.save(loan);
    }

    public Loan updateLoan(Long id, Loan loan) {
        Loan existingLoan = getLoanById(id);
        existingLoan.setBook(loan.getBook());
        existingLoan.setBorrower(loan.getBorrower());
        existingLoan.setLoanDate(loan.getLoanDate());
        existingLoan.setReturnDate(loan.getReturnDate());
        return loanRepository.save(existingLoan);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    public List<Loan> getOverdueLoans(Date currentDate) {
        return loanRepository.findByReturnDateBeforeAndBookAvailable(currentDate, true);
    }
}
