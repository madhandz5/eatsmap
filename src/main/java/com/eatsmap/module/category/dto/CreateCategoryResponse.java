package com.eatsmap.module.category.dto;

import com.eatsmap.module.category.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class CreateCategoryResponse {

    @NotEmpty
    private String categoryCode;

    @NotEmpty
    private String categoryName;

    public static CreateCategoryResponse createResponse(Category category) {
        return CreateCategoryResponse.builder()
                .categoryCode(category.getCategoryCode())
                .categoryName(category.getCategoryName())
                .build();
    }
}
