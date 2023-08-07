package com.book.assignment.web.repository;

import com.book.assignment.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {
}
