package com.eatsmap.module.groupMemberHistory;

import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.group.MemberGroupRepository;
import com.eatsmap.module.group.dto.JoinMemberToGroupRequest;
import com.eatsmap.module.group.dto.JoinMemberToGroupResponse;
import com.eatsmap.module.member.CurrentMember;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final MemberGroupRepository memberGroupRepository;

    @Transactional
    public JoinMemberToGroupResponse joinMemberToGroup(Member member, JoinMemberToGroupRequest request){
        //test
        Optional<Member> findMember = memberRepository.findById(1001L);
        Optional<MemberGroup> findGroup = memberGroupRepository.findById(request.getGroupId());
        MemberGroupHistory memberGroupHistory = MemberGroupHistory.createMemberGroupHistory();
        //join
        memberGroupHistory.joinMemberToGroup(member, findGroup.get());
        return JoinMemberToGroupResponse.createResponse(memberGroupHistoryRepository.save(memberGroupHistory));
    }

    public List<MemberGroup> getAllMemberGroup(Member member){
        return null;
    }

}
