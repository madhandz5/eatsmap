package com.eatsmap.module.follow;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.follow.dto.FollowDTO;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberService;
import com.eatsmap.module.memberNoticeHistory.MemberNoticeHistory;
import com.eatsmap.module.memberNoticeHistory.MemberNoticeHistoryService;
import com.eatsmap.module.notice.Notice;
import com.eatsmap.module.notice.NoticeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberService memberService;
    private final NoticeService noticeService;
    private final MemberNoticeHistoryService memberNoticeHistoryService;

//    follow 맺기
    @Transactional
    public FollowDTO followMember(Long toMemberId, Member fromMember){
        Member toMember = memberService.getMember(toMemberId);
        Follow follow = Follow.createFollow(toMember, fromMember);
        //notice
//        Notice notice = noticeService.getNoticeByCode("NF");
//        memberNoticeHistoryService.saveMemberNoticeHistory(MemberNoticeHistory.createMemberNoticeHistory(toMember.getId(), notice));

        return FollowDTO.createResponse(followRepository.save(follow));
    }

//    follow 취소
    public void unFollowMember(Member toMember, Member fromMember){
        Follow follow = followRepository.findByToMemberAndFromMember(toMember,fromMember);
        if(follow == null) throw new CommonException(ErrorCode.FOLLOW_NOT_FOUND);
        followRepository.delete(follow);
    }
}
