package com.library.entity;
import javax.persistence.*;
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

    @Transient
    private Long bookId;
}
