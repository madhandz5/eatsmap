package com.eatsmap.infra.utils.kakao;

import lombok.Data;

@Data
public class KakaoAuthDto {

    private String code;
    private String state;
    private String error;
    private String error_description;
}
