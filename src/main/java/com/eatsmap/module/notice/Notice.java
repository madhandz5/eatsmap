package com.eatsmap.module.notice;

import com.eatsmap.module.notice.dto.NoticeDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
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

    private boolean state;

    private LocalDateTime regDate;

    private LocalDateTime modifiedAt;

    public static Notice createNotice(NoticeDTO noticeDTO){
        return Notice.builder()
                .noticeName(noticeDTO.getNoticeName())
                .noticeCode(noticeDTO.getNoticeCode())
                .text(noticeDTO.getText())
                .link(noticeDTO.getLink())
                .state(noticeDTO.isState())
                .regDate(LocalDateTime.now())
                .build();
    }

    public void modifyNotice(NoticeDTO noticeDTO){
        this.noticeName = noticeDTO.getNoticeName();
        this.noticeCode = noticeDTO.getNoticeCode();
        this.text = noticeDTO.getText();
        this.link = noticeDTO.getLink();
        this.state = noticeDTO.isState();
        this.modifiedAt = LocalDateTime.now();
    }
}
