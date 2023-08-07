package com.book.assignment.exception.member;

public class NotFoundMember extends RuntimeException{

    public NotFoundMember(String message) {
        super(message);
    }
}
