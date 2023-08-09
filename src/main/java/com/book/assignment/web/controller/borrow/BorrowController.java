package com.book.assignment.web.controller.borrow;

import com.book.assignment.entity.Book;
import com.book.assignment.entity.Borrow;
import com.book.assignment.entity.Members;
import com.book.assignment.entity.enums.IsBorrow;
import com.book.assignment.exception.book.NotFoundBook;
import com.book.assignment.exception.member.NotFoundMember;
import com.book.assignment.web.controller.borrow.dto.BorrowFormDto;
import com.book.assignment.web.repository.BookRepository;
import com.book.assignment.web.repository.BorrowRepository;
import com.book.assignment.web.repository.MemberRepository;
import com.book.assignment.web.service.borrow.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/borrow")
public class BorrowController {

    private final BorrowService borrowService;
    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/book")
    @ResponseBody
    @Transactional
    public Map<String, String> borrowBook(@RequestBody BorrowFormDto dto, HttpSession session) {
        Map<String, String> map = new HashMap<>();
        boolean canBorrow = true;

        String loginId = (String) session.getAttribute("loginId");

        Members findMember = memberRepository.findByUsername(dto.getUsername()).orElse(null);
        Book findBook = bookRepository.findById(dto.getBookId()).orElseThrow(() -> new NotFoundBook("해당하는 책을 찾을 수 없습니다."));

        if (findMember == null) {
            map.put("message", "해당하는 회원이 없습니다.");
            canBorrow = false;
        }

        if (findMember != null && !findMember.getUsername().equals(loginId)) {
            map.put("message", "자신의 아이디로 대여를 진행해주세요.");
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

    @GetMapping("/mine")
    @Transactional
    public String myBorrow(HttpSession session, Model model) {
        String loginId = (String) session.getAttribute("loginId");
        List<Borrow> myBookList = new ArrayList<>();

        if (loginId != null) {
            myBookList = borrowRepository.findMyBorrow(loginId, IsBorrow.BORROW);

            for (Borrow borrow : myBookList) {
                Period between = Period.between(LocalDateTime.now().toLocalDate(), borrow.getReturnDate().toLocalDate());
                borrow.setRemainDate(between.getDays());
            }
        }

        model.addAttribute("myBooks", myBookList);

        return "borrow/my_borrow";
    }

    @GetMapping("/return/{bookId}")
    @Transactional
    public String returnBook(@PathVariable("bookId") Long bookId, HttpSession session) {
        String loginId = (String) session.getAttribute("loginId");
        boolean isLogin = true;

        if (loginId == null) {
            isLogin = false;
        }

        if (isLogin) {
            Borrow findMyBorrow = borrowRepository.findMyBook(loginId, bookId, IsBorrow.BORROW).orElseThrow(() -> new NotFoundBook("해당하는 책을 찾을 수 없습니다."));
            Book findBook = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundBook("해당하는 책을 찾을 수 없습니다."));
            findBook.setStock(findBook.getStock() + 1);
            findMyBorrow.setIsBorrow(IsBorrow.RETURN);
        }

        return "redirect:/";
    }
}
