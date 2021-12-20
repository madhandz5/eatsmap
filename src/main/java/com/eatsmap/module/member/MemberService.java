package com.eatsmap.module.member;

import com.eatsmap.infra.common.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Member member = Member.createAccount(request);
        member.generatedEmailCheckToken();
        return SignUpResponse.createResponse(memberRepository.save(member));
    }
    //이메일 인증
    public VerifyEmailResponse verifyByEmailToken(VerifyEmailRequest request){
        Member member = memberRepository.findByEmail(request.getEmail());
        //중복인증 여부 체크
        if(member.isVerified()) {
            throw new CommonException(ErrorCode.INVALID_VERIFICATION);
        }
        member.verifiedMemberByEmail();
        return VerifyEmailResponse.createResponse(memberRepository.save(member));
    }

    public List<GetAllResponse> getAllMembers() {
        List<GetAllResponse> memberList = memberRepository.findToGetAllResponse();

        return memberList;
    }
}
