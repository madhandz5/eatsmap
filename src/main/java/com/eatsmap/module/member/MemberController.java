package com.eatsmap.module.member;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.infra.jwt.JwtUtil;
import com.eatsmap.infra.utils.kakao.KakaoAuthDto;
import com.eatsmap.module.member.dto.*;
import com.eatsmap.module.member.validator.LoginValidator;
import com.eatsmap.module.member.validator.MemberByEmailValidator;
import com.eatsmap.module.member.validator.SignUpValidator;
import com.eatsmap.module.member.validator.VerifyEmailValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "01. Member")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/account")
public class MemberController {

    private final MemberService memberService;
    private final SignUpValidator signUpValidator;
    private final VerifyEmailValidator verifyEmailValidator;
    private final MemberByEmailValidator memberByEmailValidator;
    private final LoginValidator loginValidator;
    private final JwtUtil jwtUtil;

    @InitBinder(value = "signUpRequest")
    public void initBindSignUpRequest(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator);
    }

    @InitBinder(value = "verifyEmailRequest")
    public void initBindVerifyEmailRequest(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(verifyEmailValidator);
    }
    @InitBinder(value = "loginRequest")
    public void initBindLoginRequest(WebDataBinder webDataBinder){
        webDataBinder.addValidators(loginValidator);
    }

    @InitBinder(value = "findPasswordRequest")
    public void initBindFindPasswordRequest(WebDataBinder webDataBinder){
        webDataBinder.addValidators(memberByEmailValidator);
    }


    @ApiOperation(value = "최초 회원가입", notes = "이메일 인증 이전 회원 정보 저장")
    @PostMapping(path = "/sign-up")
    public ResponseEntity signup(@RequestBody @Valid SignUpRequest request, BindingResult result) {
        if (result.hasErrors()) {
            CommonResponse response = CommonResponse.createResponse(false, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        SignUpResponse data = memberService.signUp(request);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "이메일 인증", notes = "이메일 인증 및 토큰 발급")
    @PostMapping(path = "/verify-email")
    public ResponseEntity<CommonResponse> verifyEmailWithToken(@Valid @RequestBody VerifyEmailRequest request, BindingResult result) {
        if (result.hasErrors()) {
            CommonResponse response = CommonResponse.createResponse(false, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body(response);
        }
        VerifyEmailResponse data = memberService.verifyByEmailToken(request);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    //    로그인 완료 후 JWT Token 반환, 헤더에 참조시킬것.
    @ApiOperation(value = "일반 유저 로그인")
    @PostMapping(path = "/login/password")
    public ResponseEntity loginByPassword(@RequestBody @Valid LoginRequest request, BindingResult result) {
        if (result.hasErrors()) {
            CommonResponse response = CommonResponse.createResponse(false, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        String token = jwtUtil.encodeJwt(request.getEmail());

        memberService.loginByPassword(request, token);       //verification 등록 후 활성화

        CommonResponse response = CommonResponse.createResponse(true, token);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(path = "/login/kakao")
    public ResponseEntity loginByKakao(KakaoAuthDto request) {
        if (request.getError() != null && !request.getError().isBlank()) {
            throw new CommonException(ErrorCode.KAKAO_LOGIN_PROCESS_FAILED);
        }

//        Login - KAKAO
        Member member = memberService.loginByKakao(request);
//      Make Token
        String token = jwtUtil.encodeJwt(member.getEmail());

        CommonResponse response = CommonResponse.createResponse(true, token);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "로그아웃")
    @GetMapping("/logout")
    public ResponseEntity logout(@ApiIgnore @CurrentMember Member member, HttpServletRequest request, HttpServletResponse response) {
        memberService.logout(request, response);
        CommonResponse success = CommonResponse.createResponse(true, "success");
        return ResponseEntity.status(HttpStatus.OK).body(success);
    }

    @ApiOperation(value = "비밀번호 찾기 및 초기화")
    @GetMapping("/init/password")
    public ResponseEntity findPasswordByEmail(@Valid FindPasswordRequest request, BindingResult result){
        if(result.hasErrors()){
            CommonResponse response = CommonResponse.createResponse(false, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        FindPasswordResponse data = memberService.findPassword(request);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @ApiOperation(value = "회원정보 수정", notes = "프로필 이미지 제외")
    @PutMapping(path = "/profile")
    public ResponseEntity<CommonResponse> updateProfile(@RequestBody @Valid ModifyRequest request
                                                    , @CurrentMember Member member, BindingResult result
                                                    , MultipartFile multipartFile) {
        if (result.hasErrors()) {
            CommonResponse response = CommonResponse.createResponse(false, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        ModifyResponse data = memberService.updateProfile(member, request, multipartFile);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ApiOperation(value = "모든 정보 조회")
    @GetMapping(path = "/all")
    public List<GetAllResponse> getAllMembers() {
        return memberService.getAllMembers();
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping(path = "/member")
    public ResponseEntity exitService(@CurrentMember Member member) {
        ExitResponse data = memberService.exitService(member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
