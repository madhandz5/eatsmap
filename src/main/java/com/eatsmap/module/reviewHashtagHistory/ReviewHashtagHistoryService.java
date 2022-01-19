package com.eatsmap.module.reviewHashtagHistory;

import com.eatsmap.module.hashtag.Hashtag;
import com.eatsmap.module.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewHashtagHistoryService {

    private final ReviewHashtagHistoryRepository reviewHashtagHistoryRepository;

    @Transactional
    public void createHistory(Review review, List<Hashtag> hashtags) {
        for (Hashtag hashtag : hashtags) {
            ReviewHashtagHistory reviewHashtagHistory = ReviewHashtagHistory.createHistory(review, hashtag);
            reviewHashtagHistoryRepository.save(reviewHashtagHistory);
        }
    }

    public List<ReviewHashtagHistory> getHistoryByReview(Review review) {
        return reviewHashtagHistoryRepository.findByReview(review);
    }
}
