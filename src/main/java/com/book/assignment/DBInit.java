package com.book.assignment;

import com.book.assignment.entity.Members;
import com.book.assignment.entity.enums.MemberRole;
import com.book.assignment.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DBInit {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @PostConstruct
    public void init() {
        String encode = encoder.encode("123");
        Members member = new Members("어드민", "admin", encode, MemberRole.ROLE_ADMIN);

        memberRepository.save(member);
    }
}
