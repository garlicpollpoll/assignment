package com.book.assignment.entity.enums;

public enum MemberRole {

    ROLE_ADMIN("관리자"), ROLE_MEMBER("일반사용자");

    private String description;

    MemberRole(String description) {
        this.description = description;
    }
}
