package com.eatsmap.module.account;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.account.dto.GetAllResponse;
import com.eatsmap.module.account.dto.SignUpRequest;
import com.eatsmap.module.account.dto.SignUpResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/account")
public class MemberController {

    private final MemberService memberService;

    @PostMapping(path = "/sign-up")
    public ResponseEntity<CommonResponse> signUp(@Valid @RequestBody SignUpRequest request) {

        SignUpResponse data = memberService.signUp(request);
        CommonResponse response = CommonResponse.createResponse(true, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/all")
    public List<GetAllResponse> getAllMembers() {
        return memberService.getAllMembers();
    }
}
