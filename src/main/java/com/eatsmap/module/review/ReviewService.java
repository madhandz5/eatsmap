package com.eatsmap.module.review;

import com.eatsmap.infra.common.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.category.Category;
import com.eatsmap.module.category.CategoryService;
import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.group.MemberGroupService;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberService;
import com.eatsmap.module.restaurant.Restaurant;
import com.eatsmap.module.restaurant.RestaurantService;
import com.eatsmap.module.review.dto.CreateReviewRequest;
import com.eatsmap.module.review.dto.CreateReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final RestaurantService restaurantService;
    private final MemberGroupService memberGroupService;

    @Transactional
    public CreateReviewResponse createReview(CreateReviewRequest request) {
        Member member = memberService.getMember("alpaca@naver.com");
        Restaurant restaurant = restaurantService.getRestaurant(request.getResName(), request.getAddress());

//        방문날짜가 미래이면 예외처리
//        if (request.getVisitDate().(LocalDate.now())) {
//            throw new CommonException(ErrorCode.CONSTRAINT_PROCESS_FAIL);
//        }

        //        음식점이 없으면 새로 생성
        if (restaurant == null) {
            restaurant = restaurantService.createNewRestaurant(request.getResName(), request.getAddress(), request.getX(), request.getY());
        }

//        카테고리 코드가 없으면 예외처리
        Category category = categoryService.getCategoryCode(request.getCategory());
        if (category == null) {
            throw new CommonException(ErrorCode.CONSTRAINT_PROCESS_FAIL);
        }
        MemberGroup group = memberGroupService.getMemberGroup(request.getGroupId());

        Review review = Review.createReview(member, restaurant, group, category, request);
        reviewRepository.save(review);

        return CreateReviewResponse.createResponse(review);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
//        존재하지 않는 리뷰일 경우 예외 처리
        if (review.isEmpty()) {
            throw new CommonException(ErrorCode.CONSTRAINT_PROCESS_FAIL);
        }
        review.get().deleteReview();
        reviewRepository.save(review.get());

    }
}
