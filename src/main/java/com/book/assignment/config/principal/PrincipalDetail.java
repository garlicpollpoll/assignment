package com.book.assignment.config.principal;

import com.book.assignment.entity.Members;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class PrincipalDetail implements UserDetails {

    private Members members;

    public PrincipalDetail(Members members) {
        this.members = members;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {
            log.info("role : {}", members.getRole());
            return String.valueOf(members.getRole());
        });
        return collectors;
    }

    @Override
    public String getPassword() {
        return members.getPassword();
    }

    @Override
    public String getUsername() {
        return members.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
