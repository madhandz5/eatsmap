package com.eatsmap.module.member.validator;

import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberRepository;
import com.eatsmap.module.member.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class LoginValidator implements Validator {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginRequest request = (LoginRequest) target;
        Member member = memberRepository.findByEmailAndVerified(request.getEmail(), true);

        if(member == null){
            errors.rejectValue("email", "회원이 존재하지 않습니다.");
        }else if (!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            errors.rejectValue("password","비밀번호가 틀렸습니다.");
        }
    }
}
