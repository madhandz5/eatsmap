package com.eatsmap.module.group;

import com.eatsmap.module.group.dto.CreateMemberGroupRequest;
import com.eatsmap.module.groupMemberHistory.MemberGroupHistory;
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

    private Long createdBy;
    private String groupName;
    private LocalDateTime regDate;
    private Integer views;
    private Integer groupMemberCnt;
    private boolean deleted;

    @OneToMany(mappedBy = "memberGroup")  //다대다 -> 일대다 - 다대일
    private List<MemberGroupHistory> groupMembers = new ArrayList<>();

//    public void changeGroupMembers(List<MemberGroupHistory> members){
//        this.groupMembers = members;
//    }

    public static MemberGroup createMemberGroup(CreateMemberGroupRequest request){
        return MemberGroup.builder()
                .createdBy(request.getCreatedBy())
                .groupName(request.getGroupName())
                .views(0)
                .regDate(LocalDateTime.now())
                .deleted(false)
                .build();
    }

}
