package com.eatsmap.module.group;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.group.dto.CreateMemberGroupRequest;
import com.eatsmap.module.group.dto.MemberGroupDTO;
import com.eatsmap.module.group.dto.SimpleMemberGroupDTO;
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
import java.util.stream.Collectors;

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
    public MemberGroupDTO createMemberGroup(CreateMemberGroupRequest request, Member member) {
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
        return MemberGroupDTO.createResponse(memberGroupRepository.save(group), memberInfoDTOList);
    }

    //내가 그룹장인 그룹 조회
    public List<MemberGroupDTO> getAllCreatingGroup(Member member){
        List<MemberGroupDTO> responses = new ArrayList<>();
        List<MemberGroup> myGroups = memberGroupRepository.findAllByCreatedBy(member.getId());

        return mapperToMemberGroupDTO(myGroups);
    }

    //내가 그룹원인 그룹 조회
    public List<MemberGroupDTO> getAllInvitedGroup(Member member) {
        List<MemberGroupDTO> responses = new ArrayList<>();
        List<SimpleMemberGroupDTO> dtoList = memberGroupRepository.getAllInvitedMemberGroup(member);
        if(!dtoList.isEmpty()) {
            for (SimpleMemberGroupDTO dto : dtoList) {
                List<MemberInfoDTO> memberInfoDTOList = new ArrayList<>();
                memberGroupRepository.findById(dto.getId()).get()
                        .getGroupMembers()
                        .forEach(e -> memberInfoDTOList.add(MemberInfoDTO.mapperToMemberInfo(e.getMember())));

                responses.add(MemberGroupDTO.createResponse(dto, memberInfoDTOList));
            }
        }
        return responses;
    }

    //그룹장 : 그룹 삭제
    @Transactional
    public void deleteMemberGroup(Long groupId, Member member){
        MemberGroup group = getGroup(groupId);
        if(group.getCreatedBy() != member.getId()){
            throw new CommonException(ErrorCode.GROUP_CREATION_ACCOUNT_NOT_EQUALS);
        }
        memberGroupHistoryService.delete(group.getGroupMembers());
        memberGroupRepository.delete(group);
    }

    //그룹원 : 그룹 탈퇴
    @Transactional
    public void exitMemberGroup(Long groupId, Member member){
        MemberGroup group = getGroup(groupId);
        memberGroupHistoryService.exit(group, member);
    }

    private MemberGroup getGroup(Long groupId) {
        return memberGroupRepository.findById(groupId).orElseThrow(() -> new CommonException(ErrorCode.GROUP_NOT_FOUND));
    }

    public List<MemberGroupDTO> mapperToMemberGroupDTO(List<MemberGroup> group){
        List<MemberGroupDTO> dto = new ArrayList<>();
        if(!group.isEmpty()){
            for (MemberGroup memberGroup : group) {
                List<MemberInfoDTO> memberInfoDTOList = new ArrayList<>();
                memberGroup.getGroupMembers()
                        .forEach(e -> memberInfoDTOList.add(MemberInfoDTO.mapperToMemberInfo(e.getMember())));
                dto.add(MemberGroupDTO.createResponse(memberGroup, memberInfoDTOList));
            }
        }
        return dto;
    }
}
