package com.eatsmap.module.follow;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.memberNoticeHistory.MemberNoticeHistory;
import com.eatsmap.module.memberNoticeHistory.MemberNoticeHistoryService;
import com.eatsmap.module.notice.Notice;
import com.eatsmap.module.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;
    private final NoticeService noticeService;
    private final MemberNoticeHistoryService memberNoticeHistoryService;

//    follow 맺기
    @Transactional
    public void followMember(Member toMember,Member fromMember){
        Follow follow = Follow.createFollow(toMember, fromMember);
        //notice
        Notice notice = noticeService.getNoticeByCode("NF");
        memberNoticeHistoryService.saveMemberNoticeHistory(MemberNoticeHistory.createMemberNoticeHistory(toMember.getId(), notice));

        followRepository.save(follow);
    }

//    follow 취소
    public void unFollowMember(Member toMember, Member fromMember){
        Follow follow = followRepository.findByToMemberAndFromMember(toMember,fromMember);
        if(follow == null) throw new CommonException(ErrorCode.FOLLOW_NOT_FOUND);
        followRepository.delete(follow);
    }
}
