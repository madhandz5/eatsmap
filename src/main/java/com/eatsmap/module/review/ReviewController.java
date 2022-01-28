package com.eatsmap.module.review;

import com.eatsmap.infra.common.CommonResponse;
import com.eatsmap.module.member.CurrentMember;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.review.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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
        CreateReviewResponse data = reviewService.createReview(request, photos, member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<CommonResponse> updateReview(@RequestPart @Valid UpdateReviewRequest request,
                                                       @RequestPart @Nullable List<MultipartFile> photos,
                                                       @CurrentMember Member member) {
        UpdateReviewResponse data = reviewService.updateReview(request, photos, member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<CommonResponse> deleteReview(@RequestBody HashMap<String, Long> reviewId, @CurrentMember Member member) {
        DeleteReviewResponse data = reviewService.deleteReview(reviewId.get("reviewId"), member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/find/allReviews")
    public ResponseEntity<CommonResponse> getAllReviews() {
        List<GetReviewResponse> data = reviewService.getAllReviews();
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/timeline")
    public ResponseEntity<CommonResponse> getTimeline(@CurrentMember Member member) {
        List<GetReviewResponse> data = reviewService.getTimelineReviews(member);
        CommonResponse response = CommonResponse.createResponse(true, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
