package com.eatsmap.module.review.dto;

import com.eatsmap.infra.utils.file.Fileinfo;
import com.eatsmap.module.hashtag.Hashtag;
import com.eatsmap.module.review.Review;
import com.eatsmap.module.review.ReviewPrivacy;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class UpdateReviewResponse {

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

    public static UpdateReviewResponse createResponse(Review review) {
        UpdateReviewResponse updateReviewResponse = new UpdateReviewResponse();
        updateReviewResponse.category = review.getCategory().getCategoryName();
        updateReviewResponse.taste = review.getTaste();
        updateReviewResponse.clean = review.getClean();
        updateReviewResponse.service = review.getService();
        updateReviewResponse.content = review.getContent();
        updateReviewResponse.privacy = review.getPrivacy();
        updateReviewResponse.visitDate = review.getVisitDate();
        updateReviewResponse.regDate = review.getRegDate();
        List<Hashtag> hashtagList = review.getHashtags();
        for (Hashtag hashtag : hashtagList) {
            updateReviewResponse.hashtagNames.add(hashtag.getHashtagName());
        }
        Map<String, String> fileInfo = new HashMap<>();
        for (Fileinfo file : review.getReviewFiles()) {
            fileInfo.put("savePath", file.getSavePath());
            fileInfo.put("renameFileName", file.getRenameFileName());
            updateReviewResponse.fileInfos.add(fileInfo);
        }
        return updateReviewResponse;
    }
}
