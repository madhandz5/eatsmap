package com.eatsmap.module.verification;

import com.eatsmap.module.verification.dto.VerificationResponse;

import java.time.LocalDateTime;

public interface VerificationRepositoryExtension {
    Verification searchVerification(String key);

    VerificationResponse searchVerification(String key, LocalDateTime dateTime);
}
