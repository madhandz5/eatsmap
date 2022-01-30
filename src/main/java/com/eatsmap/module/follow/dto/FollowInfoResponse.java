package com.eatsmap.module.follow.dto;

import com.eatsmap.module.follow.Follow;
import com.eatsmap.module.member.dto.MemberInfoDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class FollowInfoResponse {

    private Long followId;
    private MemberInfoDTO followMember;

    public static FollowInfoResponse createResponse(Follow follow, MemberInfoDTO memberInfo) {
        return FollowInfoResponse.builder()
                .followId(follow.getId())
                .followMember(memberInfo)
                .build();
    }
}
