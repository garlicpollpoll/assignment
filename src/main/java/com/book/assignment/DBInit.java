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
        String encode1 = encoder.encode("1234");
        Members member = new Members("어드민", "admin", encode, MemberRole.ROLE_ADMIN);
        Members member1 = new Members("일반회원", "ks3254", encode1, MemberRole.ROLE_MEMBER);

        memberRepository.save(member);
        memberRepository.save(member1);
    }
}
