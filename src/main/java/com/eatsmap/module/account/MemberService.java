package com.eatsmap.module.account;

import com.eatsmap.module.account.dto.SignUpRequest;
import com.eatsmap.module.account.dto.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return SignUpResponse.createResponse(memberRepository.save(Member.createAccount(request)));
    }
}
