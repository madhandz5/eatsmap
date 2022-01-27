package com.eatsmap.module.group;

import com.eatsmap.module.group.dto.CreateMemberGroupRequest;
import com.eatsmap.module.groupMemberHistory.MemberGroupHistory;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.review.Review;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
public class MemberGroup {

    @Id
    @GeneratedValue
    @Column(name = "membergroup_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "group")
    private List<Review> reviews = new ArrayList<>();

    private Long createdBy;
    private String groupName;
    private LocalDateTime regDate;
    private Integer views;
    private Integer joinedGroupMemberCnt;
    private Integer totalGroupMemberCnt;
    private boolean deleted;

    @Builder.Default
    @OneToMany(mappedBy = "memberGroup")  //다대다 -> 일대다 - 다대일
    private List<MemberGroupHistory> groupMembers = new ArrayList<>();


    public static MemberGroup createMemberGroup(CreateMemberGroupRequest request, Member member) {
        return MemberGroup.builder()
                .createdBy(member.getId())
                .groupName(request.getGroupName())
                .joinedGroupMemberCnt(0)
                .totalGroupMemberCnt(request.getGroupMembers().size())
                .views(0)
                .regDate(LocalDateTime.now())
                .deleted(false)
                .build();
    }

    public void setJoinedGroupMemberCnt(int i) {
        this.joinedGroupMemberCnt = i;
    }
}
