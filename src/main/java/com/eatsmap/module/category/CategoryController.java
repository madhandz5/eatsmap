package com.eatsmap.module.category;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.category.dto.*;
import com.eatsmap.module.category.validator.CreateCategoryValidator;
import com.eatsmap.module.category.validator.UpdateCategoryValidator;
import com.eatsmap.module.member.CurrentMember;
import com.eatsmap.module.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CreateCategoryValidator createCategoryValidator;
    private final UpdateCategoryValidator updateCategoryValidator;

    @InitBinder(value = "createCategoryRequest")
    public void initCreateCategoryRequest(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createCategoryValidator);
    }

    @InitBinder(value = "updateCategoryRequest")
    public void initUpdateCategoryRequest(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(updateCategoryValidator);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<CommonResponse> createCategory(@RequestBody @Valid CreateCategoryRequest request, BindingResult result) {
        if (result.hasErrors()) {
            CommonResponse response = CommonResponse.createResponse(false, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        CreateCategoryResponse data = categoryService.createCategory(request);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<CommonResponse> updateCategory(@RequestBody @Valid UpdateCategoryRequest request, BindingResult result) {
        if (result.hasErrors()) {
            CommonResponse response = CommonResponse.createResponse(false, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        UpdateCategoryResponse data = categoryService.updateCategory(request);
        CommonResponse response = CommonResponse.createResponse(true, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<CommonResponse> deleteCategory(@RequestBody HashMap<String, Long> categoryId) {
        DeleteCategoryResponse data = categoryService.deleteCategory(categoryId.get("categoryId"));
        CommonResponse response = CommonResponse.createResponse(true, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
