package com.eatsmap.module.group;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.group.dto.*;
import com.eatsmap.module.groupMemberHistory.MemberGroupHistoryService;
import com.eatsmap.module.member.CurrentMember;
import com.eatsmap.module.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/group")
public class MemberGroupController {

    private final MemberGroupService memberGroupService;
    private final MemberGroupHistoryService memberGroupHistoryService;

    @PostMapping(path = "/create")
    public ResponseEntity<CommonResponse> createMemberGroup(@RequestBody CreateMemberGroupRequest request, @CurrentMember Member member){
//   TODO: createGroup Request 수정

        CreateMemberGroupResponse data = memberGroupService.createMemberGroup(request, member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //초대된 유저 수락
    @GetMapping (path = "/join/{groupId}")
    public ResponseEntity<CommonResponse> joinMemberToGroup(@CurrentMember Member member, @PathVariable String groupId){
        JoinMemberToGroupResponse data = memberGroupHistoryService.joinMemberToGroup(member, groupId);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<CommonResponse> getAllMemberGroup(@CurrentMember Member member){
        List<MemberGroupDTO> data = memberGroupHistoryService.getAllMemberGroup(member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
