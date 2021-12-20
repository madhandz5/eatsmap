package com.eatsmap.module.account.validator;

import com.eatsmap.module.account.MemberRepository;
import com.eatsmap.module.account.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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

        if (memberRepository.existsByEmail(request.getEmail())) {
            errors.rejectValue("email", "invalid.email", "이미 사용중인 이메일입니다.");
        }

        if (memberRepository.existsByNickname(request.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", "이미 사용중인 닉네임입니다.");
        }

        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", "invalid.passwordConfirm", "입력한 패스워드가 일치하지 않습니다.");
        }
    }
}
