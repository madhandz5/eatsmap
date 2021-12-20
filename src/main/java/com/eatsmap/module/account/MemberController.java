package com.eatsmap.module.account;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.account.dto.SignUpRequest;
import com.eatsmap.module.account.dto.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/account")
public class MemberController {

    private final MemberService memberService;

    @PostMapping(path = "/sign-up")
    public ResponseEntity<CommonResponse> signUp(@RequestBody SignUpRequest request) {

        SignUpResponse data = memberService.signUp(request);
        CommonResponse response = CommonResponse.createResponse(true, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
