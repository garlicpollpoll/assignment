package com.book.assignment.entity;

import com.book.assignment.entity.enums.MemberRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Members {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "members_id")
    private Long id;

    private String name;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public Members(String name, String username, String password, MemberRole role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
