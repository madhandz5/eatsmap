package com.eatsmap.module.category;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.category.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(path = "/create")
    public ResponseEntity<CommonResponse> createCategory(@RequestBody CreateCategoryRequest request) {
        CreateCategoryResponse data = categoryService.createCategory(request);
        CommonResponse response = CommonResponse.createResponse(true, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/update")
    public ResponseEntity<CommonResponse> updateCategory(@RequestBody UpdateCategoryRequest request) {
        UpdateCategoryResponse data = categoryService.updateCategory(request);
        CommonResponse response = CommonResponse.createResponse(true, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/delete")
    public ResponseEntity<CommonResponse> deleteCategory(@RequestBody HashMap<String, Long> categoryId) {
        DeleteCategoryResponse data = categoryService.deleteCategory(categoryId.get(categoryId));
        CommonResponse response = CommonResponse.createResponse(true, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
