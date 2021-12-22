package com.eatsmap.module.group.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateMemberGroupRequest {

    private Long createdBy;
    private String groupName;

}
