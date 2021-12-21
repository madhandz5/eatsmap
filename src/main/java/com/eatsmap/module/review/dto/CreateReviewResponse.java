package com.eatsmap.module.review.dto;

import com.eatsmap.module.review.Review;
import com.eatsmap.module.review.ReviewPrivacy;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class CreateReviewResponse {

    private String category;

    private Integer taste;
    private Integer clean;
    private Integer service;
    private String content;

    private ReviewPrivacy privacy;
    private LocalDate visitDate;
    private LocalDateTime regDate;

    public static CreateReviewResponse createResponse(Review review) {
        return CreateReviewResponse.builder()
                .category(review.getCategory().getCategoryName())
                .taste(review.getTaste())
                .clean(review.getClean())
                .service(review.getService())
                .content(review.getContent())
                .privacy(review.getPrivacy())
                .visitDate(review.getVisitDate())
                .regDate(review.getRegDate())
                .build();
    }

}
