package com.book.assignment.exception.book;

public class NotFoundBook extends RuntimeException{

    public NotFoundBook(String message) {
        super(message);
    }
}
