package com.eatsmap.module.account.validator;

import com.eatsmap.module.account.AccountRepository;
import com.eatsmap.module.account.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpRequest request = (SignUpRequest) target;

        if (accountRepository.existsByEmail(request.getEmail())) {
            errors.rejectValue("email", "invalid.email", "이미 사용중인 이메일입니다.");
        }

        if (accountRepository.existsByNickname(request.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", "이미 사용중인 닉네임입니다.");
        }
    }
}
