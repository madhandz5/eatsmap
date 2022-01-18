package com.eatsmap.module.group.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CreateMemberGroupRequest {

    private String groupName;
    @Size
    private List<Long> groupMembers;  //그룹원

}
