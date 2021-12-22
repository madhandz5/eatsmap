package com.eatsmap.module.review.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class CreateReviewRequest {

    @NotNull
    @Max(5) @Min(1)
    private Integer taste;

    @NotNull
    @Max(5) @Min(1)
    private Integer clean;

    @NotNull
    @Max(5) @Min(1)
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
    @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$")
    private String visitDate;

    @NotEmpty
    private String resName;

    @NotEmpty
    private String address;

    @NotNull
    private double x;

    @NotNull
    private double y;

}