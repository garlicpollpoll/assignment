package com.book.assignment.config.principal;

import com.book.assignment.entity.Members;
import com.book.assignment.web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("왔다감");
        Members principal = memberRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("Not Found Member")
        );
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();

        session.setAttribute("loginId", principal.getUsername());

        return new PrincipalDetail(principal);
    }
}
