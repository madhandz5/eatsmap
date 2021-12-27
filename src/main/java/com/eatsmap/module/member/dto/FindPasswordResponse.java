package com.eatsmap.module.member.dto;

import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberRole;
import com.eatsmap.module.member.MemberType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class FindPasswordResponse {

    private Long id;
    private String email;

    private MemberRole memberRole;

    private MemberType memberType;

    public static FindPasswordResponse createResponse(Member member) {
        return FindPasswordResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .memberRole(member.getMemberRole())
                .memberType(member.getMemberType())
                .build();
    }
}
