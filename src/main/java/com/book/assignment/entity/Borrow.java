package com.book.assignment.entity;

import com.book.assignment.entity.enums.IsBorrow;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Borrow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "members_id")
    private Members members;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;

    @Enumerated(value = EnumType.STRING)
    private IsBorrow isBorrow;

    public Borrow(Members members, Book book, LocalDateTime borrowDate, LocalDateTime returnDate, IsBorrow isBorrow) {
        this.members = members;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.isBorrow = isBorrow;
    }
}
