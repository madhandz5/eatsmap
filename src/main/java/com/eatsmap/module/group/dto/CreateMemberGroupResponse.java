package com.eatsmap.module.group.dto;

import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.groupMemberHistory.MemberGroupHistory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class CreateMemberGroupResponse {

    private Long id;

    private Long createdBy;
    private String groupName;
    private LocalDateTime regDate;

    private Integer totalGroupMemberCnt;
    private Integer joinedGroupMemberCnt;

    private List<MemberGroupHistory> groupMembers;

    public static CreateMemberGroupResponse createResponse(MemberGroup group){
        return CreateMemberGroupResponse.builder()
                .id(group.getId())
                .createdBy(group.getCreatedBy())
                .groupName(group.getGroupName())
                .totalGroupMemberCnt(group.getTotalGroupMemberCnt())
                .joinedGroupMemberCnt(group.getJoinedGroupMemberCnt())
                .groupMembers(group.getGroupMembers())
                .regDate(group.getRegDate())
                .build();
    }
}
