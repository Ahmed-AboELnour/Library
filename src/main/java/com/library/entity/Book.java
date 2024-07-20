package com.library.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import lombok.Data;

import java.util.Date;

import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;
    private Date publishedDate;
    private boolean available;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;

    public Book(String title, String isbn, Date publishedDate, boolean available, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
        this.available = available;
        this.author = author;
    }

    public Book(Long id,String title, String isbn, Date publishedDate, boolean available, Author author) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
        this.available = available;
        this.author = author;
    }

    public Book(Long id,String title, String isbn, Date publishedDate, boolean available) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
        this.available = available;
    }

}
