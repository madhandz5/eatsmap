package com.eatsmap.module.Category;

import com.eatsmap.module.Category.dto.CreateCategoryRequest;
import com.eatsmap.module.Category.dto.CreateCategoryResponse;
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
}
