package com.book.assignment.web.repository;

import com.book.assignment.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members, Long> {

    Optional<Members> findByUsername(String username);
}
