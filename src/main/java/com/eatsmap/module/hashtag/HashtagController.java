package com.eatsmap.module.hashtag;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.category.dto.CreateCategoryResponse;
import com.eatsmap.module.hashtag.dto.*;
import com.eatsmap.module.hashtag.validator.CreateHashtagValidator;
import com.eatsmap.module.hashtag.validator.UpdateHashtagValidator;
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
@RequestMapping(path = "/api/v1/hashtag")
public class HashtagController {

    private final HashtagService hashtagService;
    private final CreateHashtagValidator createHashtagValidator;
    private final UpdateHashtagValidator updateHashtagValidator;

    @InitBinder(value = "createHashtagRequest")
    public void initCreateHashtagRequest(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createHashtagValidator);
    }

    @InitBinder(value = "updateeHashtagRequest")
    public void initUpdateHashtagRequest(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(updateHashtagValidator);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<CommonResponse> createHashtag(@RequestBody @Valid CreateHashtagRequest request, BindingResult result) {
        if (result.hasErrors()) {
            CommonResponse response = CommonResponse.createResponse(false, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        CreateHashtagResponse data = hashtagService.createHashtag(request);
        CommonResponse response = CommonResponse.createResponse(true, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<CommonResponse> updateHashtag(@RequestBody @Valid UpdateHashtagRequest request, BindingResult result) {
        if (result.hasErrors()) {
            CommonResponse response = CommonResponse.createResponse(false, result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        UpdateHashtagResponse data = hashtagService.updateHashtag(request);
        CommonResponse response = CommonResponse.createResponse((true), data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<CommonResponse> deleteHashtag(@RequestBody HashMap<String, Long> hashtagId) {
        DeleteHashtagResponse data = hashtagService.deleteHashtag(hashtagId.get("hashtagId"));
        CommonResponse response = CommonResponse.createResponse((true), data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
