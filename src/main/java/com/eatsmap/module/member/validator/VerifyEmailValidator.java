package com.eatsmap.module.member.validator;

import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberRepository;
import com.eatsmap.module.member.dto.VerifyEmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VerifyEmailValidator implements Validator {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return VerifyEmailRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        VerifyEmailRequest request = (VerifyEmailRequest) target;
        Member member = memberRepository.findFirstByEmailOrderByIdDesc(request.getEmail());

        //회원이 존재하지 않을 때
        if(member == null){
            errors.rejectValue("email", "invalid.email", "회원이 존재하지 않습니다.");
        }else if (!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            errors.rejectValue("password", "invalid.password","비밀번호가 틀렸습니다.");
        }

        //token값 불일치
        if(member.getEmailCheckToken() == null || !member.getEmailCheckToken().equals(request.getToken())){
            errors.rejectValue("token","invalid.token","유효하지 않은 인증입니다.");
        }
        //인증시간 만료
        if(!LocalDateTime.now().isBefore(member.getEmailCheckTokenGeneratedAt().plusMinutes(3))){
            errors.rejectValue("token","invalid.token","토큰이 만료되었습니다.");
        }
    }
}
