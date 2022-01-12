package com.eatsmap.module.category.dto;

import com.eatsmap.module.category.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class DeleteCategoryResponse {

    private String categoryCode;
    private String categoryName;
    private boolean deleted;

    public static DeleteCategoryResponse createResponse(Category category) {
        return DeleteCategoryResponse.builder()
                .categoryCode(category.getCategoryCode())
                .categoryName(category.getCategoryName())
                .deleted(category.isDeleted())
                .build();
    }
}
