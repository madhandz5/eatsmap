package com.eatsmap.module.account.dto;

import com.eatsmap.module.review.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class GetAllResponse {
    private Long id;

    private String email;

    private String nickname;

    private Long reviewId;
}
