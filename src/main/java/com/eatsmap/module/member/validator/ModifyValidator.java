package com.eatsmap.module.member.validator;

import com.eatsmap.module.member.MemberRepository;
import com.eatsmap.module.member.dto.ModifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ModifyValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return ModifyRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ModifyRequest request = (ModifyRequest) target;

        if(!Pattern.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9]).{8,15}", request.getPassword())) {
            errors.rejectValue("password", "invalid.password", "비밀번호는 영문자, 특수문자, 숫자 조합 8~15자입니다.");
        }

        if(!request.getPasswordConfirm().equals(request.getPassword())) {
            errors.rejectValue("passwordConfirm", "invalid.password", "비밀번호가 일치하지 않습니다.");
        }
    }
}
