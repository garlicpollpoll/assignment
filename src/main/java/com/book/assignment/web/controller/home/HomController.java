package com.book.assignment.web.controller.home;

import com.book.assignment.entity.Book;
import com.book.assignment.web.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomController {

    private final BookRepository bookRepository;

    @GetMapping("/")
    public String home(Model model) {
        PageRequest page = PageRequest.of(0, 9);
        Page<Book> books = bookRepository.findAll(page);
        List<Book> bookList = books.stream().collect(Collectors.toList());
        model.addAttribute("books", bookList);
        return "index";
    }
}
