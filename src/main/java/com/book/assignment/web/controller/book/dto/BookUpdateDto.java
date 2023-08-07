package com.book.assignment.web.controller.book.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class BookUpdateDto implements Serializable {

    private MultipartFile image;
    @NotEmpty
    private String bookName;
    @NotEmpty
    private String publisher;
    @NotEmpty
    private String ISBN;
    @NotEmpty
    private String author;

    private int stock;
}
