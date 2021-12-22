package com.eatsmap.module.member;

import com.eatsmap.infra.common.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.infra.jwt.JwtUtil;
import com.eatsmap.module.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Member member = Member.createAccount(request);
        member.generatedEmailCheckToken();
        return SignUpResponse.createResponse(memberRepository.save(member));
    }
    //이메일 인증
    @Transactional
    public VerifyEmailResponse verifyByEmailToken(VerifyEmailRequest request){
        Member member = memberRepository.findByEmail(request.getEmail());
        //중복인증 여부 체크
        if(member.isVerified()) {
            throw new CommonException(ErrorCode.INVALID_VERIFICATION);
        }
        member.verifiedMemberByEmail();
        return VerifyEmailResponse.createResponse(memberRepository.save(member));
    }

    @Transactional
    public LoginResponse loginImpl(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail());
        member.saveLoginInfo(jwtUtil.generateToken(request.getEmail()));
        return LoginResponse.createResponse(member);
    }

    @Transactional
    public ModifyResponse modify(ModifyRequest modifyRequest, HttpServletRequest http){
        String token = http.getHeader("Authorization").substring(7);

        //user 정보 조회
        //  i) token으로 조회
        Member member = memberRepository.findByEmail(jwtUtil.extractUserEmail(token));
        member.modifyMember(modifyRequest);

        //  ii) email로 조회; userDetails.getUserName(); --> 어쨌든 token 필요
//        String email = jwtUtil.extractUserEmail(token);

        return ModifyResponse.createResponse(memberRepository.save(member));
    }

    //관계 매핑 테스트
    public List<GetAllResponse> getAllMembers() {
        List<GetAllResponse> memberList = memberRepository.findToGetAllResponse();

        return memberList;
    }

}
