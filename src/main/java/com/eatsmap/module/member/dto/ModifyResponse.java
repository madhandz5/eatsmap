package com.eatsmap.module.member.dto;

import com.eatsmap.module.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class ModifyResponse {

    private Long id;

    private String email;
    private String nickname;
    private LocalDateTime passwordModifiedAt;

    private byte[] profileImg;

    public static ModifyResponse createResponse(Member member){
        return ModifyResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .passwordModifiedAt(member.getPasswordModifiedAt())
                .profileImg(member.getProfileImage())
                .build();
    }
}
