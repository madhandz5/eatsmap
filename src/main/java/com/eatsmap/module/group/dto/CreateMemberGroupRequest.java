package com.eatsmap.module.group.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateMemberGroupRequest {

    private String groupName;
    private List<Long> groupMembers;  //그룹원

}
