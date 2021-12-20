package com.eatsmap.module.review;

import com.eatsmap.module.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "rev_id")
    private Long id;

    private Integer taste;
    private Integer clean;
    private Integer service;

    @Enumerated(EnumType.STRING)
    private ReviewPrivacy privacy;

    private LocalDateTime visitDate;
    private LocalDateTime regDate;
    private boolean deleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;


    public void setMember(Member member) {
        this.member = member;
        member.getReviews().add(this);
    }

}
