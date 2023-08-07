package com.book.assignment.web.controller.member.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberJoinDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String loginPw;
}
