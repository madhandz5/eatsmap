package com.eatsmap.module.group;

import com.eatsmap.module.member.Member;

import java.util.List;

public interface MemberGroupRepositoryExtension {

    List<MemberGroup> getAllMemberGroup(Member member);
}
