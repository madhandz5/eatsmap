package com.eatsmap.module.follow;

import com.eatsmap.module.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findByToMemberAndFromMember(Member toMember, Member fromMember);

    List<Follow> findAllByFromMember(Member fromMember);

    List<Follow> findAllByToMember(Member toMember);

    boolean existsByToMemberAndFromMember(Member toMember, Member fromMember);

}
