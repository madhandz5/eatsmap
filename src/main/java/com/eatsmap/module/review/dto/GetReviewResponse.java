package com.eatsmap.module.review.dto;

import com.eatsmap.module.hashtag.Hashtag;
import com.eatsmap.module.review.Review;
import com.eatsmap.module.review.ReviewPrivacy;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class GetReviewResponse {

    private String category;

    private Integer taste;
    private Integer clean;
    private Integer service;
    private String content;

    private List<String> hashtagNames = new ArrayList<>();

    private ReviewPrivacy privacy;
    private LocalDate visitDate;
    private LocalDateTime regDate;

    public static GetReviewResponse createResponse(Review review) {
        GetReviewResponse createReviewResponse = new GetReviewResponse();
        createReviewResponse.category = review.getCategory().getCategoryName();
        createReviewResponse.taste = review.getTaste();
        createReviewResponse.clean = review.getClean();
        createReviewResponse.service = review.getService();
        createReviewResponse.content = review.getContent();
        createReviewResponse.privacy = review.getPrivacy();
        createReviewResponse.visitDate = review.getVisitDate();
        createReviewResponse.regDate = review.getRegDate();
        for (Hashtag hashtag : review.getHashtags()) {
            createReviewResponse.hashtagNames.add(hashtag.getHashtagName());
        }
        return createReviewResponse;
    }
}
