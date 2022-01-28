package com.eatsmap.module.groupMemberHistory;

import com.eatsmap.module.group.MemberGroup;
import com.eatsmap.module.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberGroupHistoryRepository extends JpaRepository<MemberGroupHistory, Long>, MemberGroupHistoryRepositoryExtension {

    MemberGroupHistory findByMemberGroup(MemberGroup findGroup);

    int countByMemberGroupAndAcceptInvitation(MemberGroup memberGroup, Boolean b);

    MemberGroupHistory findByMemberAndMemberGroup(Member member, MemberGroup group);
}
