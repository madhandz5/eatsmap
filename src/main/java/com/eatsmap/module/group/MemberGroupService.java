package com.eatsmap.module.group;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.group.dto.CreateMemberGroupRequest;
import com.eatsmap.module.group.dto.CreateMemberGroupResponse;
import com.eatsmap.module.groupMemberHistory.MemberGroupHistory;
import com.eatsmap.module.groupMemberHistory.MemberGroupHistoryService;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.memberNoticeHistory.MemberNoticeHistory;
import com.eatsmap.module.memberNoticeHistory.MemberNoticeHistoryRepository;
import com.eatsmap.module.notice.Notice;
import com.eatsmap.module.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberGroupService {

    private final MemberGroupRepository memberGroupRepository;
    private final MemberGroupHistoryService memberGroupHistoryService;
    private final NoticeRepository noticeRepository;
    private final MemberNoticeHistoryRepository memberNoticeHistoryRepository;

    //그룹생성
    @Transactional
    public CreateMemberGroupResponse createMemberGroup(CreateMemberGroupRequest request, Member member) {
        MemberGroup group = MemberGroup.createMemberGroup(request, member);
        Notice notice = noticeRepository.findNoticeByNoticeCode("NG");
        //멤버 history 생성
        for (Long groupMember : request.getGroupMembers()) {
            memberGroupHistoryService.createMemberHistory(groupMember, group);
            //그룹 멤버에게 초대 알림 전송(notice 객체 필요)
            MemberNoticeHistory memberNotice = MemberNoticeHistory.createMemberNoticeHistory(groupMember, notice);
            memberNoticeHistoryRepository.save(memberNotice);
        }
        return CreateMemberGroupResponse.createResponse(memberGroupRepository.save(group));
    }

    public MemberGroup getMemberGroup(String groupId) {
        if(groupId.equals("my")) return null;
        return memberGroupRepository.findById(Long.parseLong(groupId)).orElseThrow(() -> new CommonException(ErrorCode.GROUP_IS_NOT_EXISTS));
    }

}
