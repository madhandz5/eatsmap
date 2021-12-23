package com.eatsmap.module.group.dto;

import lombok.Data;

@Data
public class CreateMemberGroupRequest {

    private Long createdBy;
    private String groupName;

}
