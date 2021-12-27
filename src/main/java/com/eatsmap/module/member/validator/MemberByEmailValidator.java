package com.eatsmap.module.member.validator;

import com.eatsmap.infra.common.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.member.MemberRepository;
import com.eatsmap.module.member.dto.FindPasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import scala.collection.parallel.ParIterableLike;

@Component
@RequiredArgsConstructor
public class MemberByEmailValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return FindPasswordRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FindPasswordRequest request = (FindPasswordRequest) target;

        if(memberRepository.findByEmail(request.getEmail()) == null){
            errors.rejectValue("email", "invalid.email" , "회원이 존재하지 않습니다.");
        }

    }
}
