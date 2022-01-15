package com.eatsmap.module.memberNoticeHistory;

import com.eatsmap.infra.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberNoticeHistoryService {

    private final MemberNoticeHistoryRepository memberNoticeHistoryRepository;

    @Transactional
    public void saveMemberNoticeHistory(MemberNoticeHistory history){
        memberNoticeHistoryRepository.save(history);
    }
}
