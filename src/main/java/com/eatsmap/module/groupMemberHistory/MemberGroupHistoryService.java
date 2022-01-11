package com.eatsmap.module.groupMemberHistory;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.group.MemberGroupRepository;
import com.eatsmap.module.group.dto.JoinMemberToGroupResponse;
import com.eatsmap.module.group.dto.MemberGroupDTO;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberService;
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
    private final MemberService memberService;

    //초대받은 유저 최초 등록
    @Transactional
    public void createMemberHistory(Long groupMember, MemberGroup group) {
        Member member = memberService.getMember(groupMember);
        MemberGroupHistory memberGroupHistory = MemberGroupHistory.createMemberGroupHistory(member, group);
        memberGroupHistoryRepository.save(memberGroupHistory);
    }

    //초대받은 유저 수락
    @Transactional
    public JoinMemberToGroupResponse joinMemberToGroup(Member member, String groupId){

        MemberGroup findGroup = getGroupById(groupId);
        MemberGroupHistory memberGroupHistory = getGroupHistoryByMemberGroup(findGroup);
        //join
        memberGroupHistory.joinMemberToGroup(member, findGroup);
        //group_member_cnt ++
        findGroup.joinMemberToGroup(memberGroupHistoryRepository.countByMemberGroup_Id(Long.parseLong(groupId)));
        return JoinMemberToGroupResponse.createResponse(memberGroupHistoryRepository.save(memberGroupHistory));
    }

    private MemberGroupHistory getGroupHistoryByMemberGroup(MemberGroup group) {
        MemberGroupHistory memberGroupHistory = memberGroupHistoryRepository.findByMemberGroup(group);
        if(memberGroupHistory == null) throw new CommonException(ErrorCode.GROUP_IS_NOT_EXISTS);
        return memberGroupHistory;
    }

    private MemberGroup getGroupById(String groupId) {
        Optional<MemberGroup> memberGroup = memberGroupRepository.findById(Long.parseLong(groupId));
        if(memberGroup.isEmpty()) throw new CommonException(ErrorCode.GROUP_IS_NOT_EXISTS);

        return memberGroup.get();
    }

    public List<MemberGroupDTO> getAllMemberGroup(Member member){

        //MemberGroupHistory와 MemberGroup 조인 -> MemberGroup정보 가져오기
        return memberGroupRepository.getAllMemberGroup(member);
    }


}
