package com.eatsmap.module.review;

import com.eatsmap.module.Category.Category;
import com.eatsmap.module.hashtag.Hashtag;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.review.dto.CreateReviewRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "rev_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private Integer taste;
    private Integer clean;
    private Integer service;
    private String content;

    //Groups

    //Hashtag
    @OneToOne
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @Enumerated(EnumType.STRING)
    private ReviewPrivacy privacy;

    private LocalDate visitDate;
    private LocalDateTime regDate;
    private boolean deleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;


    public static Review createReview(CreateReviewRequest request) {
//        visitDate
        String visitDate = request.getVisitDate();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Review.builder()
                .taste((request.getTaste()))
                .clean(request.getClean())
                .service(request.getService())
                .content(request.getContent())
                .privacy(ReviewPrivacy.valueOf(request.getPrivacy()))
                .visitDate(LocalDate.parse(visitDate, fmt))
                .regDate(LocalDateTime.now())
                .build();
    }

    public void setMember(Member member) {
        this.member = member;
        member.getReviews().add(this);
    }

    public void setCategory(Category category) {
        this.category = category;
        category.getReviews().add(this);
    }

    public void setHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
        hashtag.setReview(this);
    }
}
