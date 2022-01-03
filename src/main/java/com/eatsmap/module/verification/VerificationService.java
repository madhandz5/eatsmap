package com.eatsmap.module.verification;

import com.eatsmap.infra.common.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.verification.dto.VerificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VerificationService {

    private final VerificationRepository verificationRepository;

    @Transactional
    public Verification createNewVerification() {
        String makeKey = UUID.randomUUID().toString();
        Verification newVerification = Verification.createVerification(makeKey);
        return verificationRepository.save(newVerification);
    }

    @Transactional
    public Verification saveNewVerification(String key, Member member){  //유진 01/03
        Verification newVerification = Verification.createVerification(key);
        newVerification.setMember(member);
        return verificationRepository.save(newVerification);
    }

    public VerificationResponse getVerification(String key) {
        VerificationResponse verification = verificationRepository.searchVerification(key, LocalDateTime.now());
        if (verification == null) {
            throw new CommonException(ErrorCode.VERIFICATION_NOT_FOUND);
        }
        return verification;
    }

    @Transactional
    public void terminateVerification(String key) {
        Verification verification = verificationRepository.searchVerification(key);
        if (verification == null) {
            throw new CommonException(ErrorCode.VERIFICATION_NOT_FOUND);
        }
        verification.modifyChecked();
    }
}
