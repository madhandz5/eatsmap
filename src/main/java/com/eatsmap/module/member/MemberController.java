package com.eatsmap.module.member;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.member.dto.*;
import com.eatsmap.module.member.validator.SignUpValidator;
import com.eatsmap.module.member.validator.VerifyEmailValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/account")
public class MemberController {

    private final MemberService memberService;
    private final SignUpValidator signUpValidator;
    private final VerifyEmailValidator verifyEmailValidator;

    @InitBinder(value = "SignUpRequest")
    public void initBindSignUpRequest(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpValidator);
    }
    @InitBinder(value = "verifyEmailRequest")
    public void initBindVerifyEmailRequest(WebDataBinder webDataBinder){
        webDataBinder.addValidators(verifyEmailValidator);
    }

    @PostMapping(path = "/sign-up")
    public ResponseEntity<CommonResponse> signUp(@Valid @RequestBody SignUpRequest request) {

        SignUpResponse data = memberService.signUp(request);
        CommonResponse response = CommonResponse.createResponse(true, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/verify-email")
    public ResponseEntity<CommonResponse> verifyEmailWithToken(@Valid @RequestBody VerifyEmailRequest request, Errors errors){
        if(errors.hasErrors()){
            CommonResponse response = CommonResponse.createResponse(false, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        VerifyEmailResponse data = memberService.verifyByEmailToken(request);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/all")
    public List<GetAllResponse> getAllMembers() {
        return memberService.getAllMembers();
    }
}
