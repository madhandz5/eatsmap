package com.eatsmap.module.reviewHashtagHistory;

import com.eatsmap.module.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewHashtagHistoryRepository extends JpaRepository<ReviewHashtagHistory, Long> {

    List<ReviewHashtagHistory> findByReview(Review review);
}
