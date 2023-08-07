package com.book.assignment.web.service.member;

import com.book.assignment.entity.Members;
import com.book.assignment.entity.enums.MemberRole;
import com.book.assignment.web.controller.member.dto.MemberJoinDto;
import com.book.assignment.web.controller.member.dto.MemberLoginDto;
import com.book.assignment.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Members join(MemberJoinDto dto) {
        String encodePw = encoder.encode(dto.getLoginPw());

        Members member = new Members(dto.getName(), dto.getLoginId(), encodePw, MemberRole.ROLE_MEMBER);
        return memberRepository.save(member);
    }

    public boolean checkDuplicateLoginId(String loginId) {
        Members findMember = memberRepository.findByUsername(loginId).orElse(null);

        if (findMember == null) {
            return true;
        }
        else {
            return false;
        }
    }
}
