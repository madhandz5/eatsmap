package com.eatsmap.module.category;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.category.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CreateCategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = categoryRepository.save(Category.createCategory(request));
        return CreateCategoryResponse.createResponse(category);
    }

    @Transactional
    public UpdateCategoryResponse updateCategory(UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(Long.parseLong(request.getCategoryId())).orElseThrow(() -> new CommonException(ErrorCode.CATEGORY_NOT_FOUND));
        category.updateCategory(request);
        return UpdateCategoryResponse.updateResponse(category);
    }

    @Transactional
    public DeleteCategoryResponse deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new CommonException(ErrorCode.CATEGORY_NOT_FOUND));
        category.deleteCategory();
        return DeleteCategoryResponse.createResponse(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    public Category getCategoryCode(String categoryCode) {
        return categoryRepository.findByCategoryCode(categoryCode);
    }

}
