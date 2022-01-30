package com.eatsmap.module.member;

import com.eatsmap.module.member.dto.GetAllResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryExtension {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Member findFirstByEmailOrderByIdDesc(String email);

    @Query("select new com.eatsmap.module.member.dto.GetAllResponse(m.id, m.email, m.nickname, r.id) from Member m join m.reviews r")
    List<GetAllResponse> findToGetAllResponse();

    Member findByIdAndExited(Long memberId, boolean exited);

    Member findMemberByBeforePassword(String password);

    Member findByEmail(String email);

    Member findMemberByKakaoUserId(String id);
}
