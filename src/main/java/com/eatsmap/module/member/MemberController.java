package com.eatsmap.module.member;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.member.dto.*;
import com.eatsmap.module.member.validator.LoginValidator;
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
    private final LoginValidator loginValidator;

    @InitBinder(value = "signUpRequest")
    public void initBindSignUpRequest(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpValidator);
    }
    @InitBinder(value = "verifyEmailRequest")
    public void initBindVerifyEmailRequest(WebDataBinder webDataBinder){
        webDataBinder.addValidators(verifyEmailValidator);
    }
    @InitBinder(value = "loginRequest")
    public void initBindLoginRequest(WebDataBinder webDataBinder){
        webDataBinder.addValidators(loginValidator);
    }

    @PostMapping(path = "/sign-up") //토큰 인증유효기간 지난 사용자 동일 이메일로 save하는 과정에서 오류
    public ResponseEntity<CommonResponse> signUp(@Valid @RequestBody SignUpRequest request, Errors errors) {
        if(errors.hasErrors()){
            CommonResponse response = CommonResponse.createResponse(false, errors.getAllErrors());
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(response);
        }
        SignUpResponse data = memberService.signUp(request);
        CommonResponse response = CommonResponse.createResponse(true, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/verify-email")
    public ResponseEntity<CommonResponse> verifyEmailWithToken(@Valid @RequestBody VerifyEmailRequest request, Errors errors){
        if(errors.hasErrors()){
            CommonResponse response = CommonResponse.createResponse(false, errors.getAllErrors());
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(response);
        }
        VerifyEmailResponse data = memberService.verifyByEmailToken(request);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<CommonResponse> loginImpl(@Valid @RequestBody LoginRequest request, Errors errors){
        if(errors.hasErrors()){
            CommonResponse response = CommonResponse.createResponse(false, errors.getAllErrors());
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(response);
        }
        LoginResponse data = memberService.loginImpl(request);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/modify")  //jwt토큰 필요
    public ResponseEntity<CommonResponse> modifyImpl(@Valid @RequestBody ModifyRequest request, Errors errors){
        if(errors.hasErrors()){
            CommonResponse response = CommonResponse.createResponse(false, errors.getAllErrors());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        return null;
    }

    @GetMapping(path = "/all")
    public List<GetAllResponse> getAllMembers() {
        return memberService.getAllMembers();
    }
}
