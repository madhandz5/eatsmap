package com.eatsmap.module.member;

import com.eatsmap.module.member.dto.SignUpRequest;
import com.eatsmap.module.review.Review;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author : ryan
 * @version : 1.0.0
 * @package : com.eatsmap.module.account
 * @name : Account.java
 * @date : 2021/12/17 1:43 오후
 * @modifyed :
 **/
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "member_seq", sequenceName = "member_seq", initialValue = 1001)
public class Member {
    @Id
    @GeneratedValue(generator = "member_seq")
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Column(unique = true)
    private String email;
    private String password;

    @Column(unique = true)
    private String nickname;

    private boolean exited;
    private LocalDateTime exitedAt;

    private LocalDateTime regDate;
    private LocalDateTime passwordModifiedAt;
    private LocalDateTime lastLoginAt;

    private String emailCheckToken;
    private LocalDateTime emailCheckTokenGeneratedAt;
    private boolean verified;


    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    public static Member createAccount(SignUpRequest request) {

        return Member.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(request.getPassword())
                .verified(false)
                .build();
    }

    public void verifiedMemberByEmail() {
        this.verified = true;
        this.regDate = LocalDateTime.now();
    }

    public void generatedEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }
}