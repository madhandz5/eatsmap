package com.eatsmap.module.member.dto;

import com.eatsmap.module.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class LoginResponse {
    private Long id;

    private String email;
    private String nickname;
    private LocalDateTime lastLoginAt;

    public static LoginResponse createResponse(Member member){
        return LoginResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .lastLoginAt(member.getLastLoginAt())
                .build();
    }
}
