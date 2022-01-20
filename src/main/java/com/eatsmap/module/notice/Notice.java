package com.eatsmap.module.notice;

import com.eatsmap.module.notice.dto.NoticeDTO;
import com.eatsmap.module.notice.dto.NoticeModifyDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "notice_seq", sequenceName = "notice_seq", initialValue = 1001, allocationSize = 1)
public class Notice {   //추가, 수정, 삭제 => 관리자 권한

    @Id
    @GeneratedValue
    @Column(name = "notice_id")
    private Long id;

    private String noticeName;

    private String noticeCode;

    private String text;

    private String link;

    private boolean state;      //사용자 보여주기
    private boolean deleted;    //관리자 보여주기

    private LocalDateTime regDate;

    private LocalDateTime modifiedAt;

    public static Notice createNotice(NoticeDTO noticeDTO){
        return Notice.builder()
                .noticeName(noticeDTO.getNoticeName())
                .noticeCode(noticeDTO.getNoticeCode())
                .text(noticeDTO.getText())
                .link(noticeDTO.getLink())
                .state(noticeDTO.isState())
                .deleted(noticeDTO.isDeleted())
                .regDate(LocalDateTime.now())
                .build();
    }

    public void modifyNotice(NoticeModifyDTO noticeModifyDTO){
        this.noticeName = noticeModifyDTO.getNoticeName();
        this.noticeCode = noticeModifyDTO.getNoticeCode();
        this.text = noticeModifyDTO.getText();
        this.link = noticeModifyDTO.getLink();
        this.state = noticeModifyDTO.isState();
        this.deleted = noticeModifyDTO.isDeleted();
        this.modifiedAt = LocalDateTime.now();
    }
}
