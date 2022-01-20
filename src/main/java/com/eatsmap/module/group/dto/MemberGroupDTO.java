package com.eatsmap.module.group.dto;

import com.eatsmap.module.member.dto.MemberInfoDTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.List;

@Data
public class MemberGroupDTO {
    private Long id;
    private String groupName;

    private Integer joinedGroupMemberCnt;
    private Integer totalGroupMemberCnt;

    private List<MemberInfoDTO> groupMembers;

    @QueryProjection
    public MemberGroupDTO(Long id, String groupName, Integer totalGroupMemberCnt, Integer joinedGroupMemberCnt) {
        this.id = id;
        this.groupName = groupName;
        this.totalGroupMemberCnt = totalGroupMemberCnt;
        this.joinedGroupMemberCnt = joinedGroupMemberCnt;
    }

}
