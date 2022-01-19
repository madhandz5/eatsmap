package com.eatsmap.module.review;

import com.eatsmap.infra.utils.file.Fileinfo;
import com.eatsmap.module.category.Category;
import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.hashtag.Hashtag;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.restaurant.Restaurant;
import com.eatsmap.module.review.dto.CreateReviewRequest;
import com.eatsmap.module.reviewHashtagHistory.ReviewHashtagHistory;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "review_seq", sequenceName = "review_seq", initialValue = 1001, allocationSize = 30)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq")
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private Integer taste;
    private Integer clean;
    private Integer service;
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "membergroup_id")
    private MemberGroup group;

    @OneToMany(mappedBy = "review")
    private List<ReviewHashtagHistory> reviewHashtagHistories = new ArrayList<>();

    @Transient
    private List<Hashtag> hashtags;

    @Enumerated(EnumType.STRING)
    private ReviewPrivacy privacy;

    private LocalDate visitDate;
    private LocalDateTime regDate;
    private LocalDateTime modifiedAt;
    private LocalDateTime delDate;
    private boolean deleted;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "review")
    private List<Fileinfo> reviewFiles = new ArrayList<>();

    public static Review createReview(Member member, List<Fileinfo> reviewFiles, Restaurant restaurant, MemberGroup group, Category category, List<Hashtag> hashtags, CreateReviewRequest request) {
        Review review = new Review();
        review.setMember(member);
        review.setReviewFiles(reviewFiles);
        review.setRestaurant(restaurant);
        if (group != null) review.setGroup(group);
        review.setCategory(category);
        review.setHashtags(hashtags);
        review.taste = request.getTaste();
        review.service = request.getService();
        review.clean = request.getClean();
        review.content = request.getContent();
        review.regDate = LocalDateTime.now();
        review.visitDate = LocalDate.parse(request.getVisitDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        review.privacy = ReviewPrivacy.valueOf(request.getPrivacy());
        return review;
    }

    public void deleteReview() {
        this.deleted = true;
        this.delDate = LocalDateTime.now();
    }

    public void setMember(Member member) {
        this.member = member;
//        member.getReviews().add(this);
    }

    public void setCategory(Category category) {
        this.category = category;
        category.getReviews().add(this);
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        restaurant.getReviews().add(this);
    }

    public void setGroup(MemberGroup group) {
        this.group = group;
//        group.getReviews().add(this);
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public void setReviewFiles(List<Fileinfo> reviewFiles) {
        this.reviewFiles = reviewFiles;
        for (Fileinfo reviewFile : reviewFiles) {
            reviewFile.setReview(this);
        }
    }
}
