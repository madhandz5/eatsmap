package com.eatsmap.module.group;

import com.eatsmap.infra.common.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.group.dto.CreateMemberGroupRequest;
import com.eatsmap.module.group.dto.CreateMemberGroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberGroupService {

    private final MemberGroupRepository memberGroupRepository;

    //그룹생성
    @Transactional
    public CreateMemberGroupResponse createMemberGroup(CreateMemberGroupRequest request) {
        MemberGroup group = MemberGroup.createMemberGroup(request);
        return CreateMemberGroupResponse.createResponse(memberGroupRepository.save(group));
    }

    public MemberGroup getMemberGroup(String groupId) {
        if(groupId.equals("my")) return null;
        return memberGroupRepository.findById(Long.parseLong(groupId)).orElseThrow(() -> new CommonException(ErrorCode.GROUP_IS_NOT_EXISTS));
    }
}
