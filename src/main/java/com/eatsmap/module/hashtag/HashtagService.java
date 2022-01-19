package com.eatsmap.module.hashtag;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.hashtag.dto.*;
import com.eatsmap.module.review.Review;
import com.eatsmap.module.reviewHashtagHistory.ReviewHashtagHistory;
import com.eatsmap.module.reviewHashtagHistory.ReviewHashtagHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;
    private final ReviewHashtagHistoryService reviewHashtagHistoryService;

    @Transactional
    public CreateHashtagResponse createHashtag(CreateHashtagRequest request) {
        Hashtag hashtag = hashtagRepository.save(Hashtag.createHashtag(request));
        return CreateHashtagResponse.createResponse(hashtag);
    }

    @Transactional
    public UpdateHashtagResponse updateHashtag(UpdateHashtagRequest request) {
        Hashtag hashtag = hashtagRepository.findById(Long.parseLong(request.getHashtagId())).orElseThrow(() -> new CommonException(ErrorCode.HASHTAG_NOT_FOUND));
        hashtag.updateHashtag(request);
        return UpdateHashtagResponse.createResponse(hashtag);
    }

    @Transactional
    public DeleteHashtagResponse deleteHashtag(Long hashtagId) {
        Hashtag hashtag = hashtagRepository.findById(hashtagId).orElseThrow(() -> new CommonException(ErrorCode.HASHTAG_NOT_FOUND));
        hashtag.deleteHashtag();
        return DeleteHashtagResponse.createResponse(hashtag);
    }

    public Hashtag getHashtagByHashtagCode(String hashtagCode) {
        return hashtagRepository.findByHashtagCode(hashtagCode);
    }

    public Hashtag getHashtagByHashtagCodeNotDeleted(String hashtagCode) {
        return hashtagRepository.findByHashtagCodeAndDeleted(hashtagCode, false);
    }

    public List<Hashtag> getHashtagByReview(Review review) {
        List<ReviewHashtagHistory> reviewHashtagHistories = reviewHashtagHistoryService.getHistoryByReview(review);
        List<Hashtag> hashtags = new ArrayList<>();
        for (ReviewHashtagHistory reviewHashtagHistory : reviewHashtagHistories) {
            hashtags.add(reviewHashtagHistory.getHashtag());
        }
        return hashtags;
    }
}
