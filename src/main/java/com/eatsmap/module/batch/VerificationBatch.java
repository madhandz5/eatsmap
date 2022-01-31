package com.eatsmap.module.batch;

import com.eatsmap.module.verification.Verification;
import com.eatsmap.module.verification.VerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VerificationBatch {

    private final VerificationRepository verificationRepository;

    @Scheduled(cron = "0 0 0 * * *")  //1분(cron = "0 * * * * *") 개발편의상 0시
    public void verifyTokenBatch(){
        List<Verification> verifyFalse = verificationRepository.findAll().stream()
                .filter(v -> v.getExpiredAt().isBefore(LocalDateTime.now())).collect(Collectors.toList());
        verifyFalse.forEach(e -> e.modifyChecked());
        verificationRepository.saveAll(verifyFalse);
    }

}
