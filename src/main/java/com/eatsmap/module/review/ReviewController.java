package com.eatsmap.module.review;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.review.dto.CreateReviewRequest;
import com.eatsmap.module.review.dto.CreateReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping(path = "/create")
    public ResponseEntity<CommonResponse> createReview(@Valid @RequestBody CreateReviewRequest request) {
        CreateReviewResponse data = reviewService.createReview(request);
        CommonResponse response = CommonResponse.createResponse(true, data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(path = "/delete")
    public void deleteReview(@RequestBody String reviewId) {
        reviewService.deleteReview(Long.parseLong(reviewId));
    }


}
