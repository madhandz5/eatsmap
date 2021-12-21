package com.eatsmap.module.Category.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateCategoryRequest {

    @NotEmpty
    private String categoryCode;

    @NotEmpty
    private String categoryName;

}
