package com.eatsmap.module.review.reviewHashtagHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewHashtagHistoryRepository extends JpaRepository<ReviewHashtagHistory, Long> {
}
