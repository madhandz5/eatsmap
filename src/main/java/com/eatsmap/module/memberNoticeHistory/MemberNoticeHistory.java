package com.eatsmap.module.memberNoticeHistory;

import com.eatsmap.module.notice.Notice;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "member_notice_seq", sequenceName = "member_notice_seq", initialValue = 1001)
public class MemberNoticeHistory {

    @Id
    @GeneratedValue(generator = "member_notice_seq")
    @Column(name = "member_notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    private Long memberId;

    private LocalDateTime regDate;

    public static MemberNoticeHistory createMemberNoticeHistory(Long memberId, Notice notice){
        return MemberNoticeHistory.builder()
                .memberId(memberId)
                .notice(notice)
                .regDate(LocalDateTime.now())
                .build();
    }
}
