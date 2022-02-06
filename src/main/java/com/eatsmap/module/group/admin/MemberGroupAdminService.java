package com.eatsmap.module.group.admin;

import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.group.MemberGroupRepository;
import com.eatsmap.module.group.MemberGroupService;
import com.eatsmap.module.group.dto.MemberGroupDTO;
import com.eatsmap.module.member.dto.MemberInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberGroupAdminService {

    private final MemberGroupService memberGroupService;
    private final MemberGroupRepository memberGroupRepository;

    public List<MemberGroupDTO> getAllMemberGroup(){
        List<MemberGroup> all = memberGroupRepository.findAll();
        return memberGroupService.mapperToMemberGroupDTO(all);
    }
}
