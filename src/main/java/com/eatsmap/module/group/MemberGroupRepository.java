package com.eatsmap.module.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberGroupRepository extends JpaRepository<MemberGroup, Long>, MemberGroupRepositoryExtension {

    List<MemberGroup> findAllByCreatedBy(Long memberId);

}
