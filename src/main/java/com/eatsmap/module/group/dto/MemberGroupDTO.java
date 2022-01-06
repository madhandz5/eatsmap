package com.eatsmap.module.group.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import com.eatsmap.module.groupMemberHistory.MemberGroupHistory;

@Data
public class MemberGroupDTO {
    private Long id;
    private String groupName;
    private Integer groupMemberCnt;

    @QueryProjection
    public MemberGroupDTO(Long id, String groupName, Integer groupMemberCnt){
        this.id = id;
        this.groupName = groupName;
        this.groupMemberCnt = groupMemberCnt;
    }
}
