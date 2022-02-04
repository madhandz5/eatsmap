package com.eatsmap.module.group.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SimpleMemberGroupDTO {

    private Long id;

    private Long createdBy;
    private String groupName;
    private LocalDateTime regDate;

    private Integer totalGroupMemberCnt;
    private Integer joinedGroupMemberCnt;


    @QueryProjection
    public SimpleMemberGroupDTO(Long id,Long createdBy, String groupName,LocalDateTime regDate, Integer totalGroupMemberCnt, Integer joinedGroupMemberCnt) {
        this.id = id;
        this.createdBy = createdBy;
        this.groupName = groupName;
        this.regDate = regDate;
        this.totalGroupMemberCnt = totalGroupMemberCnt;
        this.joinedGroupMemberCnt = joinedGroupMemberCnt;
    }
}
