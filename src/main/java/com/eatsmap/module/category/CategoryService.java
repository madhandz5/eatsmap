package com.eatsmap.module.category;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.category.dto.CreateCategoryRequest;
import com.eatsmap.module.category.dto.CreateCategoryResponse;
import com.eatsmap.module.category.dto.DeleteCategoryResponse;
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

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    public Category getCategoryCode(String categoryCode) {
        return categoryRepository.findByCategoryCode(categoryCode);
    }

    public DeleteCategoryResponse deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new CommonException(ErrorCode.CATEGORY_IS_NOT_EXISTS));
        category.deleteCategory();
        return DeleteCategoryResponse.createResponse(category);
    }

}
