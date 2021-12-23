package com.eatsmap.module.verification;

import com.eatsmap.module.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder(access = PRIVATE)
public class Verification {
    @Id
    @GeneratedValue
    @Column(name = "verification_id")
    private Long id;

    private String key;

    private boolean checked;

    private LocalDateTime expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Verification createVerification(String key) {
        return Verification.builder()
                .key(key)
                .checked(false)
//                TODO: 개발시 편의를 위하여 토큰 만료시간 임시 지정
                .expiredAt(LocalDateTime.now().plusDays(1))
                .build();
    }

    public void setMember(Member member) {
        member.getVerificationGroup().add(this);
        this.member = member;
    }

    public void modifyChecked() {
        this.checked = true;
    }
}
