package com.book.assignment.web.controller.borrow;

import com.book.assignment.entity.Book;
import com.book.assignment.entity.Members;
import com.book.assignment.exception.book.NotFoundBook;
import com.book.assignment.web.controller.borrow.dto.BorrowFormDto;
import com.book.assignment.web.repository.BookRepository;
import com.book.assignment.web.repository.BorrowRepository;
import com.book.assignment.web.repository.MemberRepository;
import com.book.assignment.web.service.borrow.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/borrow")
public class BorrowController {

    private final BorrowService borrowService;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/book")
    @ResponseBody
    @Transactional
    public Map<String, String> borrowBook(@RequestBody BorrowFormDto dto) {
        Map<String, String> map = new HashMap<>();
        boolean canBorrow = true;
        Members findMember = memberRepository.findByUsername(dto.getUsername()).orElse(null);
        Book findBook = bookRepository.findById(dto.getBookId()).orElseThrow(() -> new NotFoundBook("해당하는 책을 찾을 수 없습니다."));

        if (findMember == null) {
            map.put("message", "해당하는 회원이 없습니다.");
            canBorrow = false;
        }

        if (findBook.getStock() <= 0) {
            map.put("message", "책의 재고가 없습니다.");
            canBorrow = false;
        }

        if (canBorrow) {
            borrowService.borrow(findMember, findBook);
            map.put("message", "책이 대여되었습니다.");
        }

        return map;
    }
}
