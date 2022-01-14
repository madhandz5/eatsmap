package com.eatsmap.module.hashtag.dto;

import com.eatsmap.module.hashtag.Hashtag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class DeleteHashtagResponse {

    private String hashtagCode;
    private String hashtagName;
    private boolean deleted;

    public static DeleteHashtagResponse createResponse(Hashtag hashtag) {
        return DeleteHashtagResponse.builder()
                .hashtagCode(hashtag.getHashtagCode())
                .hashtagName(hashtag.getHashtagName())
                .deleted(true)
                .build();
    }
}