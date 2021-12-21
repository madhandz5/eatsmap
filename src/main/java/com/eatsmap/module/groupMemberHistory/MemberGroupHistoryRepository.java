package com.eatsmap.module.groupMemberHistory;

import com.eatsmap.module.group.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberGroupHistoryRepository extends JpaRepository<MemberGroupHistory, Long> {

    MemberGroupHistory findByMemberGroup(MemberGroup findGroup);
}
