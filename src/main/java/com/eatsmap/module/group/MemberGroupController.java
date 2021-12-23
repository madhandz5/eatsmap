package com.eatsmap.module.group;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.group.dto.CreateMemberGroupRequest;
import com.eatsmap.module.group.dto.CreateMemberGroupResponse;
import com.eatsmap.module.group.dto.JoinMemberToGroupResponse;
import com.eatsmap.module.groupMemberHistory.MemberGroupHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/group")
public class MemberGroupController {

    private final MemberGroupService memberGroupService;
    private final MemberGroupHistoryService memberGroupHistoryService;

    @PostMapping(path = "/create")
    public ResponseEntity<CommonResponse> createMemberGroup(@RequestBody CreateMemberGroupRequest request){
        CreateMemberGroupResponse data = memberGroupService.createMemberGroup(request);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping(path = "/join")
    public ResponseEntity<CommonResponse> joinMemberToGroup(){
        JoinMemberToGroupResponse data = memberGroupHistoryService.joinMemberToGroup();
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
