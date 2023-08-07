package com.book.assignment.web.service.borrow;

import com.book.assignment.entity.Book;
import com.book.assignment.entity.Borrow;
import com.book.assignment.entity.Members;
import com.book.assignment.entity.enums.IsBorrow;
import com.book.assignment.web.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BorrowService {

    private final BorrowRepository borrowRepository;

    @Transactional
    public void borrow(Members member, Book book) {
        LocalDateTime now = LocalDateTime.now();
        Borrow borrow = new Borrow(member, book, now, now.plusDays(7), IsBorrow.BORROW, 7);
        book.setStock(book.getStock() - 1);
        borrowRepository.save(borrow);
    }
}
