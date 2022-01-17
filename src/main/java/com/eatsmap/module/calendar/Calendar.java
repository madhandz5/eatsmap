package com.eatsmap.module.calendar;

import com.eatsmap.module.calendar.dto.CreateCalendarRequest;
import com.eatsmap.module.calendarMemberHistory.CalendarMemberHistory;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.restaurant.Restaurant;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "calendar_seq", sequenceName = "calendar_seq", initialValue = 1001)
public class Calendar {

    @Id
    @GeneratedValue(generator = "calendar_seq")
    @Column(name = "calendar_id")
    private Long id;

    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime time;

    private Long createdBy;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Builder.Default
    @OneToMany(mappedBy = "calendar")  //다대다 -> 일대다 - 다대일
    private List<CalendarMemberHistory> calendarMembers = new ArrayList<>();

    public static Calendar createCalendar(CreateCalendarRequest request,Member member) {
        return Calendar.builder()
                .title(request.getTitle())
                .date(request.getDate())
                .time(request.getTime())
                .createdBy(member.getId())
                .build();
    }



}
