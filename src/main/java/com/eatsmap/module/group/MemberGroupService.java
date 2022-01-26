package com.eatsmap.module.group;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.group.dto.CreateMemberGroupRequest;
import com.eatsmap.module.group.dto.CreateMemberGroupResponse;
import com.eatsmap.module.groupMemberHistory.MemberGroupHistory;
import com.eatsmap.module.groupMemberHistory.MemberGroupHistoryService;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberService;
import com.eatsmap.module.member.dto.MemberInfoDTO;
import com.eatsmap.module.memberNoticeHistory.MemberNoticeHistory;
import com.eatsmap.module.memberNoticeHistory.MemberNoticeHistoryRepository;
import com.eatsmap.module.notice.Notice;
import com.eatsmap.module.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberGroupService {

    private final MemberGroupHistoryService memberGroupHistoryService;
    private final MemberService memberService;

    private final MemberGroupRepository memberGroupRepository;
    private final NoticeRepository noticeRepository;
    private final MemberNoticeHistoryRepository memberNoticeHistoryRepository;

    //그룹생성
    @Transactional
    public CreateMemberGroupResponse createMemberGroup(CreateMemberGroupRequest request, Member member) {
        MemberGroup group = MemberGroup.createMemberGroup(request, member);
        Notice notice = noticeRepository.findNoticeByNoticeCode("NG");

//        TODO: groupForm Validation
        if(request.getGroupMembers().size() == 0) throw new CommonException(ErrorCode.GROUP_MEMBER_NULL);
        List<MemberInfoDTO> memberInfoDTOList = new ArrayList<>();
        for (Long groupMemberId : request.getGroupMembers()) {

            //그룹원 history 생성
            MemberGroupHistory history = memberGroupHistoryService.createMemberHistory(groupMemberId, group);

            //그룹 멤버 조회 후, response에 전달해줄 dto 객체로 매핑
            memberInfoDTOList.add(MemberInfoDTO.mapperToMemberInfo(history.getMember()));

            //그룹 멤버에게 초대 알림 전송(notice 객체 필요)
            MemberNoticeHistory memberNotice = MemberNoticeHistory.createMemberNoticeHistory(groupMemberId, notice);
            MemberNoticeHistory saveHistory = memberNoticeHistoryRepository.save(memberNotice);
        }
        return CreateMemberGroupResponse.createResponse(memberGroupRepository.save(group), memberInfoDTOList);
    }

    public MemberGroup getMemberGroup(String groupId) {
        if(groupId.equals("my")) return null;
        return memberGroupRepository.findById(Long.parseLong(groupId)).orElseThrow(() -> new CommonException(ErrorCode.GROUP_NOT_FOUND));
    }

}
