package com.eatsmap.module.review.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateReviewRequest {

    @NotEmpty
    private Integer taste;

    @NotEmpty
    private Integer clean;

    @NotEmpty
    private Integer service;

    private String content;

    @NotNull
    private List<String> hashtag;

    @NotEmpty
    private String category;

    @NotEmpty
    private String groups;

    @NotEmpty
    private String privacy;

    @NotEmpty
    private String visitDate;

}