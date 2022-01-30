package com.eatsmap.module.follow;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.follow.dto.FollowDTO;
import com.eatsmap.module.follow.dto.FollowInfoResponse;
import com.eatsmap.module.member.CurrentMember;
import com.eatsmap.module.member.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "07. Follow")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/follow")
public class FollowController {

    private final FollowService followService;

    @ApiOperation(value = "잇친 맺기")
    @GetMapping(path = "/{toMemberId}")
    public ResponseEntity followMember(@ApiIgnore @CurrentMember Member member, @PathVariable Long toMemberId){
        FollowDTO data = followService.followMember(toMemberId, member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ApiOperation(value = "잇친 끊기")
    @GetMapping(path = "/cancel/{toMemberId}")
    public ResponseEntity unfollowMember(@ApiIgnore @CurrentMember Member member, @PathVariable Long toMemberId){
        followService.unFollowMember(toMemberId, member);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "내가 팔로우한 잇친 조회")
    @GetMapping(path = "/followings")
    public ResponseEntity getAllFollowing(@ApiIgnore @CurrentMember Member member){
        List<FollowInfoResponse> data = followService.getFollowingMembers(member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "내 팔로워 조회")
    @GetMapping(path = "/followers")
    public ResponseEntity getAllFollower(@ApiIgnore @CurrentMember Member member){
        List<FollowInfoResponse> data = followService.getAllFollower(member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
