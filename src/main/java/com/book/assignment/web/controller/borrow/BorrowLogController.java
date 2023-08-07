package com.book.assignment.web.controller.borrow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book/admin")
public class BorrowLogController {

    @GetMapping("/log")
    public String home() {
        return "book/find_book";
    }
}
