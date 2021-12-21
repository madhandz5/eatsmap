package com.eatsmap.module.group;

import com.eatsmap.module.group.dto.CreateMemberGroupRequest;
import com.eatsmap.module.group.dto.CreateMemberGroupResponse;
import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberGroupService {

    private final MemberGroupRepository memberGroupRepository;

    //그룹생성
    @Transactional
    public CreateMemberGroupResponse createMemberGroup(CreateMemberGroupRequest request){
        MemberGroup group = MemberGroup.createMemberGroup(request);
        return CreateMemberGroupResponse.createResponse(memberGroupRepository.save(group));
    }

}
