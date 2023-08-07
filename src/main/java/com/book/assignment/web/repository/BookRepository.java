package com.book.assignment.web.repository;

import com.book.assignment.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    Page<Book> findAll(Pageable pageable);

    @Query("select b from Book b where b.bookName like concat('%', :bookName, '%')")
    List<Book> findByBookName(@Param("bookName") String bookName);

    Optional<Book> findByISBN(String isbn);
}
