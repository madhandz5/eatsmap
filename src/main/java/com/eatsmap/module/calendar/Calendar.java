package com.eatsmap.module.calendar;

import lombok.*;

import javax.persistence.*;

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


}
