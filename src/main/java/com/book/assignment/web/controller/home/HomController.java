package com.book.assignment.web.controller.home;

import com.book.assignment.entity.Book;
import com.book.assignment.web.repository.BookRepository;
import com.book.assignment.web.service.page.PagingDto;
import com.book.assignment.web.service.page.PagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomController {

    private final BookRepository bookRepository;
    private final PagingService pagingService;

    @GetMapping("/")
    public String home(Model model, @RequestParam(value = "page", defaultValue = "0") Integer pageNow) {
        final int VISIBLE = 8;

        if (pageNow != 0) {
            pageNow -= 1;
        }
        PageRequest page = PageRequest.of(pageNow, VISIBLE);
        List<Book> books = bookRepository.findAllByOrder(page);

        long size = bookRepository.count();

        PagingDto pagingDto = pagingService.paging(VISIBLE, pageNow, size);

        model.addAttribute("pageCount", pagingDto.getMap());
        model.addAttribute("lastPage", pagingDto.getTotalPage());
        model.addAttribute("books", books);
        return "index";
    }
}
