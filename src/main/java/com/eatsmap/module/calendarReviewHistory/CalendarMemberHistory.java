package com.eatsmap.module.calendarReviewHistory;

import com.eatsmap.module.calendar.Calendar;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.review.Review;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "calendar_member_history_seq",sequenceName = "calendar_member_history_seq",initialValue = 1001)
public class CalendarMemberHistory {

    @Id
    @Column(name = "calendar_member_history_id")
    @GeneratedValue(generator = "calendar_review_history_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    private LocalDateTime invitedAt;

    public static CalendarMemberHistory createMemberGroupHistory(Calendar calendar, Member member){
        return CalendarMemberHistory.builder()
                .calendar(calendar)
                .member(member)
                .invitedAt(LocalDateTime.now())
                .build();
    }

    public void addSchedule(Calendar calendar, Member member){
        this.calendar = calendar;
        calendar.getCalendarMembers().add(this);

        this.member = member;
        member.getCalendarMembers().add(this);

    }

    public void removeSchedule(Calendar calendar, Member member){
//        this.member = null;
        calendar.getCalendarMembers().remove(this);

        member.getCalendarMembers().remove(this);
    }


}
