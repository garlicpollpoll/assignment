package com.book.assignment.web.controller.book;

import com.book.assignment.entity.Book;
import com.book.assignment.exception.book.NotFoundBook;
import com.book.assignment.web.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FindBookController {

    private final BookRepository bookRepository;

    @PostMapping("/book/find_by_book_name")
    public String findByBookName(@RequestParam("bookName") String bookName, Model model) {
        List<Book> findBooks = bookRepository.findByBookName(bookName);

        model.addAttribute("books", findBooks);

        return "book/find_book_only_admin";
    }

    @PostMapping("/book/find_by_ISBN")
    public String findByISBN(@RequestParam("ISBN") String isbn, Model model) {
        Book findBook = bookRepository.findByISBN(isbn).orElseThrow(() -> new NotFoundBook("해당하는 책이 없습니다."));

        model.addAttribute("books", findBook);

        return "book/find_book_only_admin";
    }

    @PostMapping("/book/find_by_book_id")
    public String findById(@RequestParam("bookId") String bookId, Model model) {
        Book findBook = bookRepository.findById(Long.parseLong(bookId)).orElseThrow(() -> new NotFoundBook("해당하는 책이 없습니다."));

        model.addAttribute("books", findBook);

        return "book/find_book_only_admin";
    }
}
