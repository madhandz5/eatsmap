package com.eatsmap.module.groupMemberHistory;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.group.MemberGroupRepository;
import com.eatsmap.module.group.dto.JoinMemberToGroupResponse;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberGroupHistoryService {

    private final MemberGroupHistoryRepository memberGroupHistoryRepository;
    private final MemberGroupRepository memberGroupRepository;
    private final MemberService memberService;

    //초대받은 유저 최초 등록
    @Transactional
    public MemberGroupHistory createMemberHistory(Long groupMemberId, MemberGroup group) {
        Member member = memberService.getMember(groupMemberId);
        MemberGroupHistory memberGroupHistory = MemberGroupHistory.createMemberGroupHistory(member, group);
        return memberGroupHistoryRepository.save(memberGroupHistory);
    }

    //초대받은 유저 수락
    @Transactional
    public JoinMemberToGroupResponse joinMemberToGroup(Member member, Long groupId){

        MemberGroup findGroup = getGroupById(groupId);
        MemberGroupHistory memberGroupHistory = getHistoryByMemberAndGroup(member, findGroup);
        log.info("find 히스토리: {}", memberGroupHistory.getId());
        //join
        memberGroupHistory.joinMemberToGroup(member, findGroup);
        MemberGroupHistory savedHistory = memberGroupHistoryRepository.save(memberGroupHistory);
        log.info("join 히스토리: {}", savedHistory.isAcceptInvitation());
        //group_member_cnt ++
        int cnt = memberGroupHistoryRepository.countByMemberGroupAndAcceptInvitation(findGroup, true);
        log.info("초대 수락cnt:{}", cnt);
        findGroup.setJoinedGroupMemberCnt(cnt);
        memberGroupRepository.save(findGroup);
        return JoinMemberToGroupResponse.createResponse(savedHistory);
    }

    private MemberGroupHistory getHistoryByMemberAndGroup(Member member, MemberGroup group) {
        MemberGroupHistory memberGroupHistory = memberGroupHistoryRepository.findByMemberAndMemberGroup(member, group);
        if(memberGroupHistory == null) throw new CommonException(ErrorCode.GROUP_NOT_FOUND);
        return memberGroupHistory;
    }

    private MemberGroupHistory getGroupHistoryByMemberGroup(MemberGroup group) {
        MemberGroupHistory memberGroupHistory = memberGroupHistoryRepository.findByMemberGroup(group);
        if(memberGroupHistory == null) throw new CommonException(ErrorCode.GROUP_NOT_FOUND);
        return memberGroupHistory;
    }

    private MemberGroup getGroupById(Long groupId) {
        return memberGroupRepository.findById(groupId).orElseThrow(() -> new CommonException(ErrorCode.GROUP_NOT_FOUND));
    }

    public void delete(List<MemberGroupHistory> historyList) {
        if(!historyList.isEmpty()) {
            memberGroupHistoryRepository.deleteAll(historyList);
        }
    }

    public void exit(MemberGroup group, Member member) {
        MemberGroupHistory history = memberGroupHistoryRepository.findByMemberAndMemberGroup(member, group);
        if(history == null) throw new CommonException(ErrorCode.GROUP_HISTORY_NOT_FOUNT);
        memberGroupHistoryRepository.delete(history);
    }
}
