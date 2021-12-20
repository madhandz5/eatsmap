package com.eatsmap.module.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllResponse {
    private Long id;

    private String email;

    private String nickname;

    private Long reviewId;
}
