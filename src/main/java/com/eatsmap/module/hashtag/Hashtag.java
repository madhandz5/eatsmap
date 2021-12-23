package com.eatsmap.module.hashtag;

import com.eatsmap.module.review.Review;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Hashtag {

    @Id
    @GeneratedValue
    @Column(name = "hashtag_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private int md01 = 0;
    private int md02 = 0;
    private int md03 = 0;
    private int md04 = 0;
    private int md05 = 0;
    private int md06 = 0;

    private int pr01 = 0;
    private int pr02 = 0;
    private int pr03 = 0;
    private int pr04 = 0;
    private int pr05 = 0;

    public void setReview(Review review) {
        this.review = review;
        review.setHashtag(this);
    }

    public void updateHashtag(List<String> hashtags) {
        for (String hashtag : hashtags) {
            switch (hashtag) {
                case "md01":
                    this.md01 = 1;
                    break;
                case "md02":
                    this.md02 = 1;
                    break;
                case "md03":
                    this.md03 = 1;
                    break;
                case "md04":
                    this.md04 = 1;
                    break;
                case "md05":
                    this.md05 = 1;
                    break;
                case "md06":
                    this.md06 = 1;
                    break;
                case "pr01":
                    this.pr01 = 1;
                    break;
                case "pr02":
                    this.pr02 = 1;
                    break;
                case "pr03":
                    this.pr03 = 1;
                    break;
                case "pr04":
                    this.pr04 = 1;
                    break;
                case "pr05":
                    this.pr05 = 1;
                    break;
                default:
                    break;
            }
        }
    }
}
