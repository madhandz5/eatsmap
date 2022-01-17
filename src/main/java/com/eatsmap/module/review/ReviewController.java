package com.eatsmap.module.review;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.member.CurrentMember;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.review.dto.CreateReviewRequest;
import com.eatsmap.module.review.dto.CreateReviewResponse;
import com.eatsmap.module.review.dto.DeleteReviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping(path = "/create")
    public ResponseEntity<CommonResponse> createReview(@RequestPart @Valid CreateReviewRequest request,
                                                       @RequestPart List<MultipartFile> photos,
                                                       @CurrentMember Member member) {
        log.info(photos.get(0).getOriginalFilename());
        CreateReviewResponse data = reviewService.createReview(request, photos, member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<CommonResponse> deleteReview(@RequestBody HashMap<String, Long> reviewId, @CurrentMember Member member) {
        DeleteReviewResponse data = reviewService.deleteReview(reviewId.get("reviewId"), member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
