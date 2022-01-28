package com.eatsmap.module.review;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.infra.utils.file.FileService;
import com.eatsmap.infra.utils.file.Fileinfo;
import com.eatsmap.module.category.Category;
import com.eatsmap.module.category.CategoryService;
import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.group.MemberGroupService;
import com.eatsmap.module.hashtag.Hashtag;
import com.eatsmap.module.hashtag.HashtagService;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.restaurant.Restaurant;
import com.eatsmap.module.restaurant.RestaurantService;
import com.eatsmap.module.review.dto.*;
import com.eatsmap.module.reviewHashtagHistory.ReviewHashtagHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CategoryService categoryService;
    private final RestaurantService restaurantService;
    private final MemberGroupService memberGroupService;
    private final HashtagService hashtagService;
    private final ReviewHashtagHistoryService reviewHashtagHistoryService;
    private final FileService fileService;

    @Transactional
    public CreateReviewResponse createReview(CreateReviewRequest request, List<MultipartFile> photos, Member member) {
//        방문날짜가 오늘 이후이면 예외처리
        if (LocalDate.parse(request.getVisitDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).isAfter(LocalDate.now())) {
            throw new CommonException(ErrorCode.REVIEW_VISIT_DATE_IS_NOT_PAST);
        }
//        음식점이 없으면 새로 생성
        Restaurant restaurant = restaurantService.getRestaurant(request.getResName(), request.getAddress());
        if (restaurant == null) {
            restaurant = restaurantService.createNewRestaurant(request.getResName(), request.getAddress(), request.getX(), request.getY());
        }
//        카테고리 코드가 없으면 예외처리
        Category category = categoryService.getCategoryCode(request.getCategory());
        if (category == null) {
            throw new CommonException(ErrorCode.CATEGORY_NOT_FOUND);
        }
//        해시태그가 없으면 예외처리
        List<Hashtag> hashtags = new ArrayList<>();
        for (String hashtag : request.getHashtag()) {
            Hashtag getHashtag = hashtagService.getHashtagByHashtagCode(hashtag);
            if (getHashtag == null) {
                throw new CommonException(ErrorCode.HASHTAG_NOT_FOUND);
            }
            hashtags.add(getHashtag);
        }
//        그룹이 내 피드라면 null 입력
        MemberGroup group = memberGroupService.getMemberGroup(request.getGroupId());
//        파일 저장
        List<Fileinfo> reviewFiles = fileService.createReviewFiles(photos);

        Review review = Review.createReview(member, reviewFiles, restaurant, group, category, hashtags, request);
        reviewRepository.save(review);
        reviewHashtagHistoryService.createHistory(review, hashtags);

        return CreateReviewResponse.createResponse(review);
    }

    @Transactional
    public DeleteReviewResponse deleteReview(Long reviewId, Member member) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()-> new CommonException(ErrorCode.REVIEW_NOT_FOUND));
        if (!review.getMember().getId().equals(member.getId())) throw new CommonException(ErrorCode.ACCESS_DENIED);

        review.deleteReview();
        return DeleteReviewResponse.createResponse(review);
    }

    @Transactional
    public UpdateReviewResponse updateReview(UpdateReviewRequest request, List<MultipartFile> photos, Member member) {
        Review review = reviewRepository.findById(Long.parseLong(request.getId())).orElseThrow(()-> new CommonException(ErrorCode.REVIEW_NOT_FOUND));
        if (!review.getMember().getId().equals(member.getId())) throw new CommonException(ErrorCode.ACCESS_DENIED);

//        방문날짜가 오늘 이후이면 예외처리
        if (LocalDate.parse(request.getVisitDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).isAfter(LocalDate.now())) {
            throw new CommonException(ErrorCode.REVIEW_VISIT_DATE_IS_NOT_PAST);
        }
//        음식점이 없으면 새로 생성
        Restaurant restaurant = restaurantService.getRestaurant(request.getResName(), request.getAddress());
        if (restaurant == null) {
            restaurant = restaurantService.createNewRestaurant(request.getResName(), request.getAddress(), request.getX(), request.getY());
        }
//        카테고리 코드가 없으면 예외처리
        Category category = categoryService.getCategoryCode(request.getCategory());
        if (category == null) {
            throw new CommonException(ErrorCode.CATEGORY_NOT_FOUND);
        }
//        해시태그가 없으면 예외처리
        List<Hashtag> hashtags = new ArrayList<>();
        for (String hashtag : request.getHashtag()) {
            Hashtag getHashtag = hashtagService.getHashtagByHashtagCode(hashtag);
            if (getHashtag == null) {
                throw new CommonException(ErrorCode.HASHTAG_NOT_FOUND);
            }
            hashtags.add(getHashtag);
        }
//        그룹이 내 피드라면 null 입력
        MemberGroup group = memberGroupService.getMemberGroup(request.getGroupId());
//        기존 파일 삭제
        fileService.deleteReviewFiles(review.getReviewFiles());
//        새로운 파일 저장
        List<Fileinfo> reviewFiles = fileService.createReviewFiles(photos);
        System.out.println("확인6");

        review.updateReview(reviewFiles, restaurant, group, category, hashtags, request);
        return UpdateReviewResponse.createResponse(review);
    }

//    삭제되지 않은 모든 리뷰
    public List<GetReviewResponse> getAllReviews() {
        List<GetReviewResponse> reviewsResponses = new ArrayList<>();
        List<Review> reviews = reviewRepository.findByDeleted(false);
        for (Review review : reviews) {
            List<Hashtag> hashtags = hashtagService.getHashtagByReview(review);
            review.setHashtags(hashtags);
            List<Fileinfo> fileinfos = fileService.getFileInfos(review);
            review.setReviewFiles(fileinfos);
            GetReviewResponse response = GetReviewResponse.createResponse(review);
            reviewsResponses.add(response);
        }
        return reviewsResponses;
    }

//    1. PRIVACY -> PUBLIC OK
//    2. MY FOLLOWINGS -> FOLLOW OK
//    3. MY REVIEWS -> PRIVATE OK
//    4. NOT DELETED
    public List<GetReviewResponse> getTimelineReviews(Member member) {
        List<GetReviewResponse> reviewsResponses = new ArrayList<>();
        List<Review> reviews = reviewRepository.findTimeline(member);
        for (Review review : reviews) {
            List<Hashtag> hashtags = hashtagService.getHashtagByReview(review);
            review.setHashtags(hashtags);
            GetReviewResponse response = GetReviewResponse.createResponse(review);
            reviewsResponses.add(response);
        }
        return reviewsResponses;
    }
}
