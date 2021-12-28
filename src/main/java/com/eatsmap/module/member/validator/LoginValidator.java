package com.eatsmap.module.member.validator;

import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberRepository;
import com.eatsmap.module.member.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Duration;
import java.time.LocalDateTime;

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
        Member member = memberRepository.memberValidateByEmail(request.getEmail());

        if(member == null){
            errors.rejectValue("email","invalid.email", "회원이 존재하지 않습니다.");
        }else if (!passwordEncoder.matches(request.getPassword(), member.getPassword())){

            if(passwordEncoder.matches(request.getPassword(), member.getBeforePassword())){
                long duration = Duration.between(LocalDateTime.now(), member.getPasswordModifiedAt()).toDays();
                errors.rejectValue("password","invalid.password","비밀번호를 변경하신지 " + duration + "일 경과했습니다.");
            }else{
                errors.rejectValue("password","invalid.password","비밀번호가 틀렸습니다.");
            }

        }
    }
}
