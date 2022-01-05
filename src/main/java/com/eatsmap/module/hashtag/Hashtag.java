package com.eatsmap.module.hashtag;

import com.eatsmap.module.hashtag.dto.CreateHashtagRequest;
import com.eatsmap.module.review.Review;
import com.eatsmap.module.reviewHashtagHistory.ReviewHashtagHistory;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "hashtag_seq", sequenceName = "hashtag_seq", initialValue = 1001, allocationSize = 30)
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hashtag_seq")
    @Column(name = "hashtag_id")
    private Long id;

    private String hashtagCode;

    private String hashtagName;

    @OneToMany(mappedBy = "hashtag")
    private List<ReviewHashtagHistory> reviewHashtagHistories;

    public static Hashtag createHashtag(CreateHashtagRequest request) {
        return Hashtag.builder()
                .hashtagCode(request.getHashtagCode())
                .hashtagName(request.getHashtagName())
                .build();
    }
}
