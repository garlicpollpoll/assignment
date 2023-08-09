package com.book.assignment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String bookName;
    private String publisher;
    private String ISBN;
    private String author;
    private int stock;
    private String url;

    public Book(String bookName, String publisher, String ISBN, String author, int stock, String url) {
        this.bookName = bookName;
        this.publisher = publisher;
        this.ISBN = ISBN;
        this.author = author;
        this.stock = stock;
        this.url = url;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
