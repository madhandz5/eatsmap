package com.eatsmap.module.follow;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.follow.dto.FollowDTO;
import com.eatsmap.module.member.CurrentMember;
import com.eatsmap.module.member.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "07. Follow")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/follow")
public class FollowController {

    private final FollowService followService;

    @ApiOperation(value = "잇친 맺기")
    @GetMapping(path = "/{toMemberId}")
    public ResponseEntity followMember(@CurrentMember Member member, @PathVariable Long toMemberId){
        FollowDTO data = followService.followMember(toMemberId, member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
