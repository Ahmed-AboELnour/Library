package com.library.entity;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private String borrower;
    private Date loanDate;
    private Date returnDate;

    public Loan( Book book, String borrower, Date loanDate, Date returnDate) {
        this.book = book;
        this.borrower = borrower;
        this.loanDate = loanDate;
        this.returnDate = returnDate;

    }

    public Loan( Long id, String borrower,Book book,Date loanDate, Date returnDate) {
        this.id = id;
        this.borrower = borrower;
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = returnDate;

    }
}
