package com.eatsmap.module.review;

import com.eatsmap.module.category.Category;
import com.eatsmap.module.hashtag.Hashtag;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.review.dto.CreateReviewRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "review_seq",sequenceName = "review_seq",initialValue = 1001, allocationSize = 30)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq")
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private Integer taste;
    private Integer clean;
    private Integer service;
    private String content;

    //Groups

    //Hashtag
    @OneToOne(mappedBy = "review")
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
