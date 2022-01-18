package com.eatsmap.module.review.dto;

import com.eatsmap.module.hashtag.Hashtag;
import com.eatsmap.module.review.Review;
import com.eatsmap.module.review.ReviewPrivacy;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateReviewResponse {

    private String category;

    private Integer taste;
    private Integer clean;
    private Integer service;
    private String content;

    private List<String> hashtagNames = new ArrayList<>();

    private ReviewPrivacy privacy;
    private LocalDate visitDate;
    private LocalDateTime regDate;

    public static CreateReviewResponse createResponse(Review review, List<Hashtag> hashtags) {
        CreateReviewResponse createReviewResponse = new CreateReviewResponse();
        createReviewResponse.category = review.getCategory().getCategoryName();
        createReviewResponse.taste = review.getTaste();
        createReviewResponse.clean = review.getClean();
        createReviewResponse.service = review.getService();
        createReviewResponse.content = review.getContent();
        createReviewResponse.privacy = review.getPrivacy();
        createReviewResponse.visitDate = review.getVisitDate();
        createReviewResponse.regDate = review.getRegDate();
        return createReviewResponse;
//        return CreateReviewResponse.builder()
//                .category(review.getCategory().getCategoryName())
//                .taste(review.getTaste())
//                .clean(review.getClean())
//                .service(review.getService())
//                .content(review.getContent())
//                .privacy(review.getPrivacy())
//                .visitDate(review.getVisitDate())
//                .regDate(review.getRegDate())
//                .build();
    }
}
