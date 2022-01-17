package com.eatsmap.module.category.validator;

import com.eatsmap.module.category.CategoryRepository;
import com.eatsmap.module.category.dto.CreateCategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CreateCategoryValidator implements Validator{

    private final CategoryRepository categoryRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateCategoryRequest request = (CreateCategoryRequest) target;

        if (categoryRepository.existsByCategoryCode(request.getCategoryCode())) {
            errors.rejectValue("categoryCode", "invalid.categoryCode", "카테고리 코드가 이미 존재합니다.");
        }
        if (categoryRepository.existsByCategoryName(request.getCategoryName())) {
            errors.rejectValue("categoryName", "invalid.categoryName", "카테고리 이름이 이미 존재합니다.");
        }
    }

}