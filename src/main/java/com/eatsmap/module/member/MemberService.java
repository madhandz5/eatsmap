package com.eatsmap.module.member;

import com.eatsmap.infra.common.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.infra.jwt.JwtUtil;
import com.eatsmap.infra.utils.kakao.KakaoAccountInfoDto;
import com.eatsmap.infra.utils.kakao.KakaoAuthDto;
import com.eatsmap.infra.utils.kakao.KakaoOAuth;
import com.eatsmap.module.member.dto.*;
import com.eatsmap.module.verification.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        return SignUpResponse.createResponse(memberRepository.save(member));
    }

    //이메일 인증
    @Transactional
    public VerifyEmailResponse verifyByEmailToken(VerifyEmailRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail());
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
        Member member = getAccount(email);
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
        Member target = memberRepository.findByEmail(kakaoInfo.getEmail());
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


//        Member member = memberRepository.findByEmail(request.getEmail());
//        member.saveLoginInfo(jwtUtil.generateToken(request.getEmail()));
//        return LoginResponse.createResponse(member);

    @Transactional
    public ModifyResponse modify(ModifyRequest modifyRequest, HttpServletRequest http) {
        String token = http.getHeader("Authorization").substring(7);

        //user 정보 조회
        //  i) token으로 조회
        Member member = memberRepository.findByEmail(jwtUtil.extractUserEmail(token));
        member.modifyMember(modifyRequest);

        //  ii) email로 조회; userDetails.getUserName(); --> 어쨌든 token 필요
//        String email = jwtUtil.extractUserEmail(token);

        return ModifyResponse.createResponse(memberRepository.save(member));
    }

    @Transactional
    public Member createNewAccount(KakaoSignUpRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword());
        Member member = Member.createAccount(request);
        member.completeSignUp();
        return memberRepository.save(member);
    }

    //관계 매핑 테스트
    public List<GetAllResponse> getAllMembers() {
        List<GetAllResponse> memberList = memberRepository.findToGetAllResponse();

        return memberList;
    }

    public Member getAccount(String email) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new CommonException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        return member;
    }

    public Member getAccount(Long memberId) {
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
}
