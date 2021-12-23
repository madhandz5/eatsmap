package com.eatsmap.infra.utils.kakao;

import lombok.Data;

@Data
public class KakaoAccessTokenDto {

    private String token_type;
    private String access_token;
    private String refresh_token;

    private int expires_in; //토큰 만료시간
    private int refresh_token_expires_in; //리프레시 종료시간
    private String scope;

}
