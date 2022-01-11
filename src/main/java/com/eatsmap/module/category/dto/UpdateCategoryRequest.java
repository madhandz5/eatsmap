package com.eatsmap.module.category.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateCategoryRequest {

    @NotEmpty
    private String categoryCode;

    @NotEmpty
    private String categoryName;

}
