package com.eatsmap.infra.utils.kakao;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonDeserialize(using = KakaoAccountInfoDeserializer.class)
public class KakaoAccountInfoDto {

    private String id;
    private boolean hasEmail;

    private String email;
    private String nickname;
}
