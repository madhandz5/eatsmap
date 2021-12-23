package com.eatsmap.module.groupMemberHistory;

import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "member_group_history_seq",sequenceName = "member_group_history_seq",initialValue = 1001)
public class MemberGroupHistory {

    @Id
    @Column(name = "member_group_history_seq")
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
