package com.eatsmap.module.follow.dto;

import com.eatsmap.module.follow.Follow;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class FollowDTO {

    private Long followId;
    private Long toMemberId;
    private Long fromMemberId;

    public static FollowDTO createResponse(Follow follow){
        return FollowDTO.builder()
                .followId(follow.getId())
                .toMemberId(follow.getToMember().getId())
                .fromMemberId(follow.getFromMember().getId())
                .build();
    }
}
