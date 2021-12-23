package com.eatsmap.module.member.dto;

import com.eatsmap.infra.utils.kakao.KakaoAccountInfoDto;
import com.eatsmap.module.member.MemberType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class KakaoSignUpRequest {

    private MemberType memberType;
    private String email;
    private String nickname;
    private String password;
    private boolean kakaoAuthAgreement;
    private String kakaoUserId;


    public static KakaoSignUpRequest createKakaoSignUpRequest(KakaoAccountInfoDto infoDto) {
        return KakaoSignUpRequest.builder()
                .memberType(MemberType.KAKAO)
                .email(infoDto.getEmail())
                .nickname(infoDto.getNickname())
                .password(String.valueOf(infoDto.getId()))
                .kakaoAuthAgreement(true)
                .kakaoUserId(String.valueOf(infoDto.getId()))
                .build();
    }

}
