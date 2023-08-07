package com.book.assignment.web.controller.member.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberLoginDto {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
