package com.eatsmap.module.member;

import com.eatsmap.module.member.dto.GetAllResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Member findByEmail(String email);

    @Query("select new com.eatsmap.module.member.dto.GetAllResponse(m.id, m.email, m.nickname, r.id) from Review r join r.member m")
    List<GetAllResponse> findToGetAllResponse();
}
