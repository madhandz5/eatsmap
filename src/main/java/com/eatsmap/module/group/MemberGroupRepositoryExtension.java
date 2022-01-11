package com.eatsmap.module.group;

import com.eatsmap.module.group.dto.MemberGroupDTO;
import com.eatsmap.module.member.Member;

import java.util.List;

public interface MemberGroupRepositoryExtension {

    List<MemberGroupDTO> getAllMemberGroup(Member member);
}
