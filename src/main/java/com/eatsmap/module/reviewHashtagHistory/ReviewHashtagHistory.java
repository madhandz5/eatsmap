package com.eatsmap.module.reviewHashtagHistory;

import com.eatsmap.module.hashtag.Hashtag;
import com.eatsmap.module.review.Review;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "review_hashtag_seq", sequenceName = "review_hashtag_seq", initialValue = 1001, allocationSize = 30)
public class ReviewHashtagHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_hashtag_seq")
    @Column(name = "review_hashtag_history_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    public static ReviewHashtagHistory createHistory(Review review, Hashtag hashtag) {
        return ReviewHashtagHistory.builder()
                .review(review)
                .hashtag(hashtag)
                .build();
    }
}
