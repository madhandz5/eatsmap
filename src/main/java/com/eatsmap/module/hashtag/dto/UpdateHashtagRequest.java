package com.eatsmap.module.hashtag.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateHashtagRequest {

    @NotEmpty
    private String hashtagId;

    @NotEmpty
    private String hashtagCode;

    @NotEmpty
    private String hashtagName;

    private boolean state;

}