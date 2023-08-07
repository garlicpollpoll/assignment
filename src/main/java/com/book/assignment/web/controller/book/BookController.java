package com.book.assignment.web.controller.book;

import com.book.assignment.entity.Book;
import com.book.assignment.exception.book.NotFoundBook;
import com.book.assignment.web.controller.book.dto.BookRegisterDto;
import com.book.assignment.web.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping("/detail/{bookId}")
    public String bookDetail(@PathVariable("bookId") Long bookId, Model model) {
        Book findBook = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundBook("책을 찾을 수 없습니다."));

        model.addAttribute("book", findBook);

        return "book/detail";
    }
}
