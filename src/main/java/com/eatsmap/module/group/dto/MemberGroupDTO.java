package com.eatsmap.module.group.dto;

import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.member.dto.MemberInfoDTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class MemberGroupDTO {
    private Long id;

    private Long createdBy;
    private String groupName;
    private LocalDateTime regDate;

    private Integer totalGroupMemberCnt;
    private Integer joinedGroupMemberCnt;

    private List<MemberInfoDTO> groupMembers;   //그룹원 정보

    public static MemberGroupDTO createResponse(MemberGroup group, List<MemberInfoDTO> memberInfoDTOList){
        return MemberGroupDTO.builder()
                .id(group.getId())
                .createdBy(group.getCreatedBy())
                .groupName(group.getGroupName())
                .totalGroupMemberCnt(group.getTotalGroupMemberCnt())
                .joinedGroupMemberCnt(group.getJoinedGroupMemberCnt())
                .groupMembers(memberInfoDTOList)
                .regDate(group.getRegDate())
                .build();
    }





}
