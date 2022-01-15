package com.eatsmap.module.follow;

import com.eatsmap.module.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findByToMemberAndFromMember(Member toMember, Member fromMember);
}
