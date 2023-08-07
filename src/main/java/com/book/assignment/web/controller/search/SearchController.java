package com.book.assignment.web.controller.search;

import com.book.assignment.entity.Book;
import com.book.assignment.web.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final BookRepository bookRepository;

    @PostMapping("/search")
    public String search(@RequestParam("bookName") String bookName, Model model) {
        List<Book> books = bookRepository.findByBookName(bookName);

        model.addAttribute("books", books);

        return "search/search_book";
    }
}
