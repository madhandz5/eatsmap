package com.eatsmap.module.hashtag.dto;

import com.eatsmap.module.hashtag.Hashtag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder(access = AccessLevel.PRIVATE)
public class CreateHashtagResponse {

    private String hashtagCode;
    private String hashtagName;

    public static CreateHashtagResponse createResponse(Hashtag hashtag) {
        return CreateHashtagResponse.builder()
                .hashtagCode(hashtag.getHashtagCode())
                .hashtagName(hashtag.getHashtagName())
                .build();
    }
}