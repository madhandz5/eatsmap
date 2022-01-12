package com.eatsmap.module.category.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCategoryRequest {

    @NotEmpty
    private String categoryId;

    @NotEmpty
    private String categoryCode;

    @NotEmpty
    private String categoryName;

}
