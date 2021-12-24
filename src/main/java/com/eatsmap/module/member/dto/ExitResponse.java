package com.eatsmap.module.member.dto;

import com.eatsmap.module.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class ExitResponse {

    private Long id;
    private String email;
    private String nickname;
    private boolean exited;
    private LocalDateTime exitedAt;

    public static ExitResponse createResponse(Member member){
        return ExitResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .exited(member.isExited())
                .exitedAt(member.getExitedAt())
                .build();
    }
}
