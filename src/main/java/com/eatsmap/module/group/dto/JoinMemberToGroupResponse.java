package com.eatsmap.module.group.dto;

import com.eatsmap.module.groupMemberHistory.MemberGroupHistory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class JoinMemberToGroupResponse {

    private Long groupId;
    private String groupName;
    private LocalDateTime joinedAt;

    public static JoinMemberToGroupResponse createResponse(MemberGroupHistory memberGroupHistory){
        return JoinMemberToGroupResponse.builder()
                .groupId(memberGroupHistory.getMemberGroup().getId())
                .groupName(memberGroupHistory.getMemberGroup().getGroupName())
                .joinedAt(memberGroupHistory.getJoinedAt())
                .build();
    }
}
