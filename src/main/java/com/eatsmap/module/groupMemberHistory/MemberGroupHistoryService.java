package com.eatsmap.module.groupMemberHistory;

import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.group.MemberGroupRepository;
import com.eatsmap.module.group.dto.JoinMemberToGroupResponse;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberGroupHistoryService {

    private final MemberGroupHistoryRepository memberGroupHistoryRepository;
    private final MemberRepository memberRepository;
    private final MemberGroupRepository memberGroupRepository;

    @Transactional
    public JoinMemberToGroupResponse joinMemberToGroup(){
        //test
        Optional<Member> findMember = memberRepository.findById(1001L);
        Optional<MemberGroup> findGroup = memberGroupRepository.findById(1L);
        MemberGroupHistory memberGroupHistory = MemberGroupHistory.createMemberGroupHistory();
        //join
        memberGroupHistory.joinMemberToGroup(findMember.get(), findGroup.get());
        return JoinMemberToGroupResponse.createResponse(memberGroupHistoryRepository.save(memberGroupHistory)) ;
    }

}
