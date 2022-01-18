package com.eatsmap.module.review.dto;

import com.eatsmap.module.hashtag.Hashtag;
import com.eatsmap.module.restaurant.Restaurant;
import com.eatsmap.module.review.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class DeleteReviewResponse {

    private Long id;
    private boolean deleted;
    private LocalDateTime delDate;

    public static DeleteReviewResponse createResponse(Review review) {
        return DeleteReviewResponse.builder()
                .id(review.getId())
                .deleted(review.isDeleted())
                .delDate(review.getDelDate())
                .build();
    }

}
