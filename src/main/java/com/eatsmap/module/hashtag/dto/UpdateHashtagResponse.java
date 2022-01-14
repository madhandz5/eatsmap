package com.eatsmap.module.hashtag.dto;

import com.eatsmap.module.hashtag.Hashtag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class UpdateHashtagResponse {

    private String hashtagCode;
    private String hashtagName;

    public static UpdateHashtagResponse createResponse(Hashtag hashtag) {
        return UpdateHashtagResponse.builder()
                .hashtagCode(hashtag.getHashtagCode())
                .hashtagName(hashtag.getHashtagName())
                .build();
    }
}