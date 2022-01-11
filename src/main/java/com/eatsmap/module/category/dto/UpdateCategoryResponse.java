package com.eatsmap.module.category.dto;

import com.eatsmap.module.category.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class UpdateCategoryResponse {

    private String categoryCode;
    private String categoryName;

    public static UpdateCategoryResponse updateResponse(Category category) {
        return UpdateCategoryResponse.builder()
                .categoryCode(category.getCategoryCode())
                .categoryName(category.getCategoryName())
                .build();
    }
}
