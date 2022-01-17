package com.eatsmap.module.follow;

import com.eatsmap.module.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "follow_seq", sequenceName = "follow_seq", initialValue = 1001, allocationSize = 1)
public class Follow {

    @Id
    @GeneratedValue(generator = "follow_seq")
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_member_id")
    private Member toMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_member_id")
    private Member fromMember;

    public static Follow createFollow(Member toMember, Member fromMember){
        return Follow.builder()
                .toMember(toMember)
                .fromMember(fromMember)
                .build();
    }
}
