package com.eatsmap.module.groupMemberHistory;

import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.group.MemberGroupRepository;
import com.eatsmap.module.group.dto.JoinMemberToGroupResponse;
import com.eatsmap.module.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberGroupHistoryService {

    private final MemberGroupHistoryRepository memberGroupHistoryRepository;
    private final MemberGroupRepository memberGroupRepository;

    @Transactional
    public JoinMemberToGroupResponse joinMemberToGroup(Member member, String groupId){

        Optional<MemberGroup> findGroup = memberGroupRepository.findById(Long.parseLong(groupId));
        MemberGroupHistory memberGroupHistory = MemberGroupHistory.createMemberGroupHistory();
        //join
        memberGroupHistory.joinMemberToGroup(member, findGroup.get());
        return JoinMemberToGroupResponse.createResponse(memberGroupHistoryRepository.save(memberGroupHistory));
    }

    public List<MemberGroup> getAllMemberGroup(Member member){

        //MemberGroupHistory와 MemberGroup 조인 -> MemberGroup정보 가져오기
        return memberGroupRepository.getAllMemberGroup(member);
    }

}
