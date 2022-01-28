package com.eatsmap.module.review.dto;

import com.eatsmap.infra.utils.file.Fileinfo;
import com.eatsmap.module.hashtag.Hashtag;
import com.eatsmap.module.review.Review;
import com.eatsmap.module.review.ReviewPrivacy;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CreateReviewResponse {

    private String category;

    private Integer taste;
    private Integer clean;
    private Integer service;
    private String content;

    private List<String> hashtagNames = new ArrayList<>();
    private List<Map<String, String>> fileInfos = new ArrayList<>();

    private ReviewPrivacy privacy;
    private LocalDate visitDate;
    private LocalDateTime regDate;

    public static CreateReviewResponse createResponse(Review review) {
        CreateReviewResponse createReviewResponse = new CreateReviewResponse();
        createReviewResponse.category = review.getCategory().getCategoryName();
        createReviewResponse.taste = review.getTaste();
        createReviewResponse.clean = review.getClean();
        createReviewResponse.service = review.getService();
        createReviewResponse.content = review.getContent();
        createReviewResponse.privacy = review.getPrivacy();
        createReviewResponse.visitDate = review.getVisitDate();
        createReviewResponse.regDate = review.getRegDate();
        List<Hashtag> hashtagList = review.getHashtags();
        for (Hashtag hashtag : hashtagList) {
            createReviewResponse.hashtagNames.add(hashtag.getHashtagName());
        }
        Map<String, String> fileInfo = new HashMap<>();
        for (Fileinfo file : review.getReviewFiles()) {
            fileInfo.put("savePath", file.getSavePath());
            fileInfo.put("renameFileName", file.getRenameFileName());
            createReviewResponse.fileInfos.add(fileInfo);
        }
        return createReviewResponse;
    }
}
