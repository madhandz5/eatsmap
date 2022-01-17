package com.eatsmap.module.member.validator;

import com.eatsmap.module.member.MemberRepository;
import com.eatsmap.module.member.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class SignUpValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpRequest request = (SignUpRequest) target;

        //verified == true && exited == false 인 회원 중에서만 중복 검증
        if (memberRepository.guestValidateByEmail(request.getEmail()) != null) {
            errors.rejectValue("email", "invalid.email", "이미 사용중인 이메일입니다.");
        }

        if (memberRepository.memberValidateByNickname(request.getNickname()) != null) {
            errors.rejectValue("nickname", "invalid.nickname", "이미 사용중인 닉네임입니다.");
        }

        if(!Pattern.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9]).{8,15}", request.getPassword())) {
            errors.rejectValue("password", "invalid.password", "비밀번호는 영문자, 특수문자, 숫자 조합 8~15자입니다.");
        }
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", "invalid.passwordConfirm", "입력한 패스워드가 일치하지 않습니다.");
        }
    }
}
