package com.eatsmap.module.category.dto;

import com.eatsmap.module.category.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class CreateCategoryResponse {

    private String categoryCode;
    private String categoryName;
    private boolean state;

    public static CreateCategoryResponse createResponse(Category category) {
        return CreateCategoryResponse.builder()
                .categoryCode(category.getCategoryCode())
                .categoryName(category.getCategoryName())
                .state(category.isState())
                .build();
    }
}
