package com.eatsmap.module.group.dto;

import com.eatsmap.module.group.MemberGroup;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class CreateMemberGroupResponse {

    private Long id;

    private Long createdBy;
    private String groupName;
    private LocalDateTime regDate;


    public static CreateMemberGroupResponse createResponse(MemberGroup group){
        return CreateMemberGroupResponse.builder()
                .id(group.getId())
                .createdBy(group.getCreatedBy())
                .groupName(group.getGroupName())
                .regDate(group.getRegDate())
                .build();
    }
}
