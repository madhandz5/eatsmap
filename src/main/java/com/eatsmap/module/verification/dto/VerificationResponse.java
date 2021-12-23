package com.eatsmap.module.verification.dto;

import com.eatsmap.module.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VerificationResponse {

    private String key;
    private LocalDateTime expiredAt;
    private Member member;

}
