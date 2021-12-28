package com.eatsmap.module.member;

import com.eatsmap.module.groupMemberHistory.MemberGroupHistory;
import com.eatsmap.module.member.dto.KakaoSignUpRequest;
import com.eatsmap.module.member.dto.ModifyRequest;
import com.eatsmap.module.member.dto.SignUpRequest;
import com.eatsmap.module.review.Review;
import com.eatsmap.module.verification.Verification;
import lombok.*;
import org.hibernate.annotations.Type;

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

    private String email;
    private String password;

    private String beforePassword;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] profileImage;

    @Column(unique = true)
    private String kakaoUserId;

    private String nickname;

    private boolean kakaoAuth;

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

    @OneToMany(mappedBy = "member")   //양방향
    private List<MemberGroupHistory> groups = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Verification> verificationGroup = new ArrayList<>();

    //    EMAIL
    public static Member createAccount(SignUpRequest request) {

        return Member.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(request.getPassword())
                .memberRole(MemberRole.GUEST)
                .memberType(MemberType.EMAIL)
                .verified(false)
                .build();
    }

    //    KAKAO
    public static Member createAccount(KakaoSignUpRequest request) {

        return Member.builder()
                .memberType(request.getMemberType())
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(request.getPassword())
                .memberRole(MemberRole.GUEST)
                .verified(false)
                .build();
    }

    public void modifyMember(Member member, ModifyRequest request) {
        this.nickname = request.getNickname();
        this.beforePassword = member.getPassword();
        this.password = request.getPassword();
        this.passwordModifiedAt = LocalDateTime.now();
    }

    public void verifiedMemberByEmail() {
        this.verified = true;
        this.regDate = LocalDateTime.now();
        this.memberRole = MemberRole.USER;
        this.memberType = MemberType.EMAIL;
    }

    public void generatedEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public void setLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public void completeSignUp() {
        this.memberRole = MemberRole.USER;
        this.regDate = LocalDateTime.now();
    }

    public void updateToTmpPassword(Member member){
        this.beforePassword = member.getPassword();
        this.password = UUID.randomUUID().toString();
    }

    public void memberExit() {
        this.exited = true;
        this.exitedAt = LocalDateTime.now();
    }

    public void setReview(Review review) {
        this.getReviews().add(review);
        review.setMember(this);
    }
}
