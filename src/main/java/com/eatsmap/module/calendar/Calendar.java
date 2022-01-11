package com.eatsmap.module.calendar;

import com.eatsmap.module.calendar.dto.CreateCalendarRequest;
import com.eatsmap.module.category.Category;
import com.eatsmap.module.category.dto.CreateCategoryRequest;
import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.hashtag.Hashtag;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.restaurant.Restaurant;
import com.eatsmap.module.review.Review;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "calendar_seq", sequenceName = "calendar_seq", initialValue = 1001)
public class Calendar {

    @Id
    @GeneratedValue(generator = "calendar_seq")
    @Column(name = "calendar_seq")
    private Long id;

    private String title;
    private String date;
    private String time;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public static Calendar createCalendar(CreateCalendarRequest request) {
        return Calendar.builder()
                .title(request.getTitle())
                .date(request.getDate())
                .time(request.getTime())
                .build();
    }









}
