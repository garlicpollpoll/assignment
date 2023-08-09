package com.book.assignment.web.controller.member;

import com.book.assignment.config.principal.PrincipalDetailService;
import com.book.assignment.entity.Members;
import com.book.assignment.web.controller.member.dto.CheckLoginIdDto;
import com.book.assignment.web.controller.member.dto.MemberJoinDto;
import com.book.assignment.web.controller.member.dto.MemberLoginDto;
import com.book.assignment.web.repository.MemberRepository;
import com.book.assignment.web.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private boolean isCheckDuplicateLoginId = false;

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/join")
    public String join(Model model) {
        MemberJoinDto dto = new MemberJoinDto();
        model.addAttribute("join", dto);
        return "member/join";
    }

    @PostMapping("/join")
    public String joinPost(@Validated @ModelAttribute("join") MemberJoinDto dto, BindingResult bindingResult) {
        log.info("isCheckDuplicateLoginId : {}", isCheckDuplicateLoginId);

        if (!isCheckDuplicateLoginId) {
            bindingResult.reject("PleaseCheckLoginIdDuplicate");
            return "member/join";
        }
        if (bindingResult.hasErrors()) {
            return "member/join";
        }

        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        boolean isMatch = Pattern.matches(pattern, dto.getLoginPw());

        if (isMatch) {
            bindingResult.reject("PasswordPatternNotMatch");
            return "member/join";
        }

        memberService.join(dto);
        SecurityContextHolder.clearContext();
        isCheckDuplicateLoginId = false;
        return "redirect:/login";
    }

    @PostMapping("/check/loginId")
    @ResponseBody
    public Map<String, String> checkLoginId(@RequestBody CheckLoginIdDto dto) {
        boolean isDuplicate = memberService.checkDuplicateLoginId(dto.getLoginId());
        Map<String, String> map = new HashMap<>();

        if (isDuplicate) {
            map.put("message", "사용가능한 아이디입니다.");
            isCheckDuplicateLoginId = true;
        }
        else {
            map.put("message", "중복된 아이디입니다.");
        }

        if (dto.getLoginId().isBlank() || dto.getLoginId().isEmpty()) {
            map.put("message", "빈 칸은 사용할 수 없습니다.");
        }

        return map;
    }

    @GetMapping("/login")
    public String login(Model model) {
        MemberLoginDto dto = new MemberLoginDto();
        model.addAttribute("login", dto);
        return "member/login";
    }

    @PostMapping("/login")
    public String loginPost(@Validated @ModelAttribute("login") MemberLoginDto dto, BindingResult bindingResult, HttpSession session) {
        Members findMember = memberRepository.findByUsername(dto.getUsername()).orElse(null);

        if (findMember == null) {
            bindingResult.reject("NotFoundMember");
            return "member/login";
        }

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(findMember.getRole().toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(findMember.getUsername(), "", authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setAttribute("loginId", findMember.getUsername());
        return "redirect:/";
    }
}
