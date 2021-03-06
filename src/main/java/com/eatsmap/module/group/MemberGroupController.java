package com.eatsmap.module.group;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.group.dto.*;
import com.eatsmap.module.groupMemberHistory.MemberGroupHistoryService;
import com.eatsmap.module.member.CurrentMember;
import com.eatsmap.module.member.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "02. Group")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/group")
public class MemberGroupController {

    private final MemberGroupService memberGroupService;
    private final MemberGroupHistoryService memberGroupHistoryService;

    @ApiOperation(value = "그룹 최초 생성")
    @PostMapping(path = "/create")
    public ResponseEntity<CommonResponse> createMemberGroup(@RequestBody CreateMemberGroupRequest request, @ApiIgnore @CurrentMember Member member){
        MemberGroupDTO data = memberGroupService.createMemberGroup(request, member);
        if(data == null){
            CommonResponse response = CommonResponse.createResponse(false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ApiOperation(value = "그룹원 그룹 가입", notes = "그룹원으로 초대된 유저 수락 및 그룹 가입")
    @GetMapping (path = "/join/{groupId}")
    public ResponseEntity<CommonResponse> joinMemberToGroup(@ApiIgnore @CurrentMember Member member, @PathVariable Long groupId){
        JoinMemberToGroupResponse data = memberGroupHistoryService.joinMemberToGroup(member, groupId);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ApiOperation(value = "내가 그룹장인 그룹 목록 조회")
    @GetMapping(path = "/my/groups")
    public ResponseEntity<CommonResponse> getAllMyGroup(@ApiIgnore @CurrentMember Member member){
        List<MemberGroupDTO> data = memberGroupService.getAllCreatingGroup(member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "내가 그룹원인 그룹 목록 조회")
    @GetMapping(path = "/groups")
    public ResponseEntity<CommonResponse> getAllMemberGroup(@ApiIgnore @CurrentMember Member member){
        List<MemberGroupDTO> data = memberGroupService.getAllInvitedGroup(member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
