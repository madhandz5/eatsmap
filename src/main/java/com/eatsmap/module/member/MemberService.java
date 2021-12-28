package com.eatsmap.module.member;

import com.eatsmap.infra.common.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.infra.utils.kakao.KakaoAccountInfoDto;
import com.eatsmap.infra.utils.kakao.KakaoAuthDto;
import com.eatsmap.infra.utils.kakao.KakaoOAuth;
import com.eatsmap.module.member.dto.*;
import com.eatsmap.module.verification.Verification;
import com.eatsmap.module.verification.VerificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationService verificationService;
    private final KakaoOAuth kakaoOAuth;


    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Member member = Member.createAccount(request);
        member.generatedEmailCheckToken();
//        TODO : Send Mail
        return SignUpResponse.createResponse(memberRepository.save(member));
    }

    //이메일 인증
    @Transactional
    public VerifyEmailResponse verifyByEmailToken(VerifyEmailRequest request) {
        Member member = memberRepository.findFirstByEmailOrderByIdDesc(request.getEmail());
        //중복인증 여부 체크
        if (member.isVerified()) {
            throw new CommonException(ErrorCode.INVALID_VERIFICATION);
        }
        member.verifiedMemberByEmail();
        return VerifyEmailResponse.createResponse(memberRepository.save(member));
    }

    @Transactional
    public void loginByPassword(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        Member member = getMember(email);
//        invalid password
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CommonException(ErrorCode.LOGIN_PROCESS_PASSWORD_NOTMATCH);
        }

//        Check email Not Verified
        if (checkEmailVerified(email)) {
            throw new CommonException(ErrorCode.VERIFICATION_NOT_FOUND);
        }
        login(member, member.getPassword());
    }

    @Transactional
    public Member loginByKakao(KakaoAuthDto kakaoAuthDto) {
//        1. Get Access Token
        String accessToken = kakaoOAuth.getAccessToken(kakaoAuthDto);

//        2. Get Kakao Account
        KakaoAccountInfoDto kakaoInfo = kakaoOAuth.getKakaoInfo(accessToken);

//        3. Check Exist Member & Sign Up
//        Member target = memberRepository.findByEmail(kakaoInfo.getEmail());   //email null 인 경우 존재
        Member target = memberRepository.findMemberByKakaoUserId(kakaoInfo.getId());

        if (target == null) {
            KakaoSignUpRequest request = KakaoSignUpRequest.createKakaoSignUpRequest(kakaoInfo);
            target = createNewAccount(request);
        }
        login(target, accessToken);
        return target;
    }

    private boolean checkEmailVerified(String email) {
        return memberRepository.checkEmailVerified(email);
    }


    @Transactional
    public void login(Member member, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new MemberAccount(member), password, List.of(new SimpleGrantedAuthority(member.getMemberRole().toString())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        member.setLastLoginAt();
    }


    @Transactional
    public ModifyResponse updateProfile(Member member, ModifyRequest request) {
        member.modifyMember(member, request);
        return ModifyResponse.createResponse(memberRepository.save(member));
    }

    @Transactional
    public Member createNewAccount(KakaoSignUpRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Member member = Member.createAccount(request);
        member.completeSignUp();
        return memberRepository.save(member);
    }

    //관계 매핑 테스트
    public List<GetAllResponse> getAllMembers() {
        List<GetAllResponse> memberList = memberRepository.findToGetAllResponse();

        return memberList;
    }

    public FindPasswordResponse findPassword(FindPasswordRequest request){
        //update to tmp password
        Member member = getMember(request.getEmail());
        member.updateToTmpPassword(member);

        //send email

        return FindPasswordResponse.createResponse(memberRepository.save(member));
    }

    public Member getMember(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new CommonException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        return member;
    }

    public Member getMember(Long memberId) {
        Member member = memberRepository.findByIdAndExited(memberId, false);
        if (member == null) {
            throw new CommonException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        return member;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new CommonException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        return new MemberAccount(member);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        1. Get Current Auth
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        2. Check Current Member is Logged in
        if (authentication == null) {
            throw new CommonException(ErrorCode.LOGOUT_DONE);
        }

        MemberAccount principal = (MemberAccount) authentication.getPrincipal();
        MemberType memberType = principal.getMember().getMemberType();
        String kakaoUserId = principal.getMember().getKakaoUserId();

//        3. Logout.
        new SecurityContextLogoutHandler().logout(request, response, authentication);

//        3-1. Kakao Logout.

        if (memberType.equals(MemberType.KAKAO)) {
            kakaoOAuth.logoutByKakaoUserId(kakaoUserId);
        }
    }

    @Transactional
    public void checkVerificationKey(String key, String email) {
        Member member = getMember(email);

//        현재 Member의 확인되지 않은 Verification 필터
        Optional<Verification> verify = member.getVerificationGroup().stream().filter(verification -> !verification.isChecked()).findFirst();

//        Verification 존재 및 일치 여부 확인
        if (verify.isEmpty()) {
            throw new CommonException(ErrorCode.VERIFICATION_NOT_FOUND);
        } else if (!key.equals(verify.get().getKey())) {
            throw new CommonException(ErrorCode.VERIFICATION_NOT_CORRECT);
        }

//        Expire verification Key
        verificationService.terminateVerification(key);


    }

    @Transactional
//    TODO : Response DTO 만들어서 리턴할것.
    public ExitResponse exitService(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        findMember.memberExit();
        return ExitResponse.createResponse(memberRepository.save(findMember));
    }
}
