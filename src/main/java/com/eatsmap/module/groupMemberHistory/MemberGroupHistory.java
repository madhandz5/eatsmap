package com.eatsmap.module.groupMemberHistory;

import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.member.Member;
import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class MemberGroupHistory {

    @Id
    @Column(name = "membergroup_history_id")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membergroup_id")
    private MemberGroup memberGroup;

    private boolean acceptInvitation;
    private LocalDateTime joinedAt;

    public static MemberGroupHistory createMemberGroupHistory(){
        return MemberGroupHistory.builder()
                .acceptInvitation(false)
                .build();
    }

    public void joinMemberToGroup(Member member, MemberGroup memberGroup){
        this.member = member;
        member.getGroups().add(this);

        this.memberGroup = memberGroup;
        memberGroup.getGroupMembers().add(this);

        this.joinedAt = LocalDateTime.now();
        this.acceptInvitation = true;

    }

    public void exitMemberToGroup(Member member, MemberGroup memberGroup){
//        this.member = null;
        member.getGroups().remove(this);

        memberGroup.getGroupMembers().remove(this);
    }


}
