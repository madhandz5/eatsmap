package com.eatsmap.module.account.dto;

import com.eatsmap.module.account.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class SignUpResponse {

    private Long id;

    private String email;
    private String name;
    private String nickname;

    public static SignUpResponse createResponse(Member member) {
        return SignUpResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}
