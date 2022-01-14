package com.eatsmap.module.hashtag;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.category.dto.CreateCategoryResponse;
import com.eatsmap.module.hashtag.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/hashtag")
public class HashtagController {

    private final HashtagService hashtagService;

    @PostMapping(path = "/create")
    public ResponseEntity<CommonResponse> createHashtag(@RequestBody @Valid CreateHashtagRequest request) {
        CreateHashtagResponse data = hashtagService.createHashtag(request);
        CommonResponse response = CommonResponse.createResponse(true, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/update")
    public ResponseEntity<CommonResponse> updateHashtag(@RequestBody @Valid UpdateHashtagRequest request) {
        UpdateHashtagResponse data = hashtagService.updateHashtag(request);
        CommonResponse response = CommonResponse.createResponse((true), data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/delete")
    public ResponseEntity<CommonResponse> deleteHashtag(@RequestBody HashMap<String, Long> hashtagId) {
        DeleteHashtagResponse data = hashtagService.deleteHashtag(hashtagId.get("hashtagId"));
        CommonResponse response = CommonResponse.createResponse((true), data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
