package com.book.assignment.web.repository;

import com.book.assignment.entity.Borrow;
import com.book.assignment.entity.enums.IsBorrow;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BorrowRepository extends JpaRepository<Borrow, Long> {

    @Query("select bo from Borrow bo join fetch bo.members m join fetch bo.book b where bo.isBorrow = :isBorrow and m.username = :username")
    List<Borrow> findMyBorrow(@Param("username") String username, @Param("isBorrow") IsBorrow isBorrow);

    @Query("select bo from Borrow bo join fetch bo.members m join fetch bo.book b where m.username = :username and b.id = :bookId and bo.isBorrow = :isBorrow")
    Optional<Borrow> findMyBook(@Param("username") String username, @Param("bookId") Long bookId, @Param("isBorrow") IsBorrow isBorrow);
}
