package com.eatsmap.module.review;

import com.eatsmap.module.category.Category;
import com.eatsmap.module.category.CategoryRepository;
import com.eatsmap.module.hashtag.Hashtag;
import com.eatsmap.module.hashtag.HashtagService;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberRepository;
import com.eatsmap.module.restaurant.Restaurant;
import com.eatsmap.module.restaurant.RestaurantRepository;
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
    private final RestaurantRepository restaurantRepository;
    private final HashtagService hashtagService;


    @Transactional
    public CreateReviewResponse createReview(CreateReviewRequest request) {
        Member member = memberRepository.findByEmail("alpaca@naver.com");
        Category category = categoryRepository.findByCategoryCode(request.getCategory());

        Review review = Review.createReview(request);
        review.setMember(member);
        review.setCategory(category);

//        음식점이 없으면 새로 생성
        Restaurant storedRestaurant = restaurantRepository.findByResNameAndAddress(request.getResName(), request.getAddress());
        if (storedRestaurant == null) {
            Restaurant restaurant = new Restaurant();
            restaurant.createRestaurant(request);
            review.setRestaurant(restaurant);
        } else {
            review.setRestaurant(storedRestaurant);
        }

        Hashtag hashtag = new Hashtag();
        hashtag.updateHashtag(request.getHashtag());
        review.setHashtag(hashtag);

        reviewRepository.save(review);
        hashtagService.createHashtag(hashtag);
        restaurantRepository.save(review.getRestaurant());

        return CreateReviewResponse.createResponse(review);
    }
}
