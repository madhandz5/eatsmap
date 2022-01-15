package com.eatsmap.module.notice;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public Notice getNoticeByCode(String code){
        Notice notice = noticeRepository.findNoticeByNoticeCode(code);
        if(notice == null){
            throw new CommonException(ErrorCode.NOTICE_NOT_FOUND);
        }
        return notice;
    }

}
