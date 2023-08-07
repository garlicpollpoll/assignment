package com.book.assignment.web.service.book;

import com.book.assignment.cloud.s3.S3Uploader;
import com.book.assignment.entity.Book;
import com.book.assignment.web.controller.book.dto.BookRegisterDto;
import com.book.assignment.web.controller.book.dto.BookUpdateDto;
import com.book.assignment.web.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public void uploadBook(BookRegisterDto dto) throws IOException {
        String url = s3Uploader.upload(dto.getImage(), "static");
        Book book = new Book(dto.getBookName(), dto.getPublisher(), dto.getISBN(), dto.getAuthor(), dto.getStock(), url);

        bookRepository.save(book);
    }

    @Transactional
    public void updateBook(Book book, BookUpdateDto dto) {
        if (dto.getImage().isEmpty()) {
            book.setUrl(book.getUrl());
        }

        book.setBookName(dto.getBookName());
        book.setAuthor(dto.getAuthor());
        book.setISBN(dto.getISBN());
        book.setPublisher(dto.getPublisher());
        book.setStock(dto.getStock());
    }
}
