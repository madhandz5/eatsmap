package com.eatsmap.module.member.dto;

import com.eatsmap.module.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class MemberInfoDTO {

    private Long id;
    private String nickname;
    private String email;

    public static MemberInfoDTO mapperToMemberInfo(Member member){
        return MemberInfoDTO.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();
    }
}
