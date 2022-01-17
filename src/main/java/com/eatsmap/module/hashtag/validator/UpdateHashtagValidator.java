package com.eatsmap.module.hashtag.validator;

import com.eatsmap.module.hashtag.HashtagRepository;
import com.eatsmap.module.hashtag.dto.UpdateHashtagRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UpdateHashtagValidator implements Validator {

    private final HashtagRepository hashtagRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UpdateHashtagRequest request = (UpdateHashtagRequest) target;

        if (hashtagRepository.existsByHashtagCode(request.getHashtagCode())) {
            errors.rejectValue("hashtagCode", "invalid.hashtagCode", "해시태그 코드가 이미 존재합니다.");
        }
        if (hashtagRepository.existsByHashtagName(request.getHashtagName())) {
            errors.rejectValue("hashtagName", "invalid.hashtagName", "해시태그 이름이 이미 존재합니다.");
        }
    }
}
