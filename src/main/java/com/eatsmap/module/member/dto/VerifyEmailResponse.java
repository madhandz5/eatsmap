package com.eatsmap.module.member.dto;

import com.eatsmap.module.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class VerifyEmailResponse {

    private Long id;

    private String email;

    private String nickname;

    private LocalDateTime regDate;

    public static VerifyEmailResponse createResponse(Member member) {
        return VerifyEmailResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .regDate(member.getRegDate())
                .build();
    }
}
