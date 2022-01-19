package com.eatsmap.module.group;

import com.eatsmap.module.group.dto.MemberGroupDTO;
import com.eatsmap.module.member.Member;
import com.querydsl.core.Tuple;

import java.util.List;

public interface MemberGroupRepositoryExtension {

    List<MemberGroupDTO> getAllMemberGroup(Member member);

    List<String> getAllGroupMemberNickname();   //admin

    List<Tuple> getAllGroupMembers();
    List<Tuple> getAllGroup();
}
