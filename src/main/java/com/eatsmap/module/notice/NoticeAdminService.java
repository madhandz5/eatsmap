package com.eatsmap.module.notice;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.notice.dto.NoticeDTO;
import com.eatsmap.module.notice.dto.NoticeModifyDTO;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeAdminService {

    private NoticeRepository noticeRepository;

    @Transactional
    public Notice addNotice(NoticeDTO noticeDTO){
        Notice notice = Notice.createNotice(noticeDTO);
        return noticeRepository.save(notice);
    }

    @Transactional
    public Notice modifyNotice(NoticeModifyDTO noticeModifyDTO){
        if(!noticeRepository.existsById(noticeModifyDTO.getId())){
            throw new CommonException(ErrorCode.NOTICE_NOT_FOUND);
        }
        Notice notice = noticeRepository.findById(noticeModifyDTO.getId()).get();
        notice.modifyNotice(noticeModifyDTO);
        return noticeRepository.save(notice);
    }

    //관리자의 삭제 가능 여부는 일단 보류!
}
