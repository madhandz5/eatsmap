package com.eatsmap.module.follow;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.follow.dto.FollowDTO;
import com.eatsmap.module.follow.dto.FollowInfoResponse;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberService;
import com.eatsmap.module.member.dto.MemberInfoDTO;
import com.eatsmap.module.memberNoticeHistory.MemberNoticeHistory;
import com.eatsmap.module.memberNoticeHistory.MemberNoticeHistoryService;
import com.eatsmap.module.notice.Notice;
import com.eatsmap.module.notice.NoticeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberService memberService;
    private final NoticeService noticeService;
    private final MemberNoticeHistoryService memberNoticeHistoryService;

//    내가 follow한 멤버 조회
    public List<FollowInfoResponse> getFollowingMembers(Member fromMember){
        List<Follow> follows = followRepository.findAllByFromMember(fromMember);
        List<FollowInfoResponse> responses = new ArrayList<>();
        if(follows.size() > 0){
            for (Follow follow : follows) {
                responses.add(FollowInfoResponse.createResponse(follow, MemberInfoDTO.mapperToMemberInfo(follow.getToMember())));
            }
        }
        return responses;
    }

//    내 팔로워 조회
    public List<FollowInfoResponse> getAllFollower(Member toMember){
        List<Follow> follows = followRepository.findAllByToMember(toMember);
        List<FollowInfoResponse> responses = new ArrayList<>();
        if(follows.size() > 0){
            for (Follow follow : follows) {
                responses.add(FollowInfoResponse.createResponse(follow, MemberInfoDTO.mapperToMemberInfo(follow.getFromMember())));
            }
        }
        return responses;
    }

//    follow 맺기
    @Transactional
    public FollowDTO followMember(Long toMemberId, Member fromMember){
        Member toMember = memberService.getMember(toMemberId);
        if(followRepository.existsByToMemberAndFromMember(toMember, fromMember)){
            throw new CommonException(ErrorCode.ALREADY_EXISTED_FOLLOW);
        }
        Follow follow = Follow.createFollow(toMember, fromMember);
        //notice
//        Notice notice = noticeService.getNoticeByCode("NF");
//        memberNoticeHistoryService.saveMemberNoticeHistory(MemberNoticeHistory.createMemberNoticeHistory(toMember.getId(), notice));

        return FollowDTO.createResponse(followRepository.save(follow));
    }

//    follow 취소
    @Transactional
    public void unFollowMember(Long toMemberId, Member fromMember){
        Member toMember = memberService.getMember(toMemberId);
        if(!followRepository.existsByToMemberAndFromMember(toMember, fromMember)){
            throw new CommonException(ErrorCode.FOLLOW_NOT_FOUND);
        }
        Follow follow = followRepository.findByToMemberAndFromMember(toMember,fromMember);
        followRepository.delete(follow);
    }
}
