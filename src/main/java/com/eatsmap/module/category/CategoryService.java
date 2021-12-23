package com.eatsmap.module.category;

import com.eatsmap.module.category.dto.CreateCategoryRequest;
import com.eatsmap.module.category.dto.CreateCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Category getCategoryCode(String categoryCode) {
        return categoryRepository.findByCategoryCode(categoryCode);
    }
}
