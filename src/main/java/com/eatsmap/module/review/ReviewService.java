package com.eatsmap.module.review;

import com.eatsmap.module.category.Category;
import com.eatsmap.module.category.CategoryRepository;
import com.eatsmap.module.hashtag.Hashtag;
import com.eatsmap.module.hashtag.HashtagService;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberRepository;
import com.eatsmap.module.review.dto.CreateReviewRequest;
import com.eatsmap.module.review.dto.CreateReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final HashtagService hashtagService;


    @Transactional
    public CreateReviewResponse createReview(CreateReviewRequest request) {
        Member member = memberRepository.findByEmail("alpaca@naver.com");
        Category category = categoryRepository.findByCategoryCode(request.getCategory());

        Review review = Review.createReview(request);
        review.setMember(member);
        review.setCategory(category);

        Hashtag hashtag = new Hashtag();
        hashtag.updateHashtag(request.getHashtag());
        review.setHashtag(hashtag);

        reviewRepository.save(review);
        hashtagService.createHashtag(hashtag);

        return CreateReviewResponse.createResponse(review);
    }
}
