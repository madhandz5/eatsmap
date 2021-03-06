package com.eatsmap.module.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long>, HashtagRepositoryExtension{
    Hashtag findByHashtagCode(String hashtagCode);

    boolean existsByHashtagCode(String hashtagCode);

    boolean existsByHashtagName(String hashtagName);

    Hashtag findByHashtagCodeAndDeleted(String hashtagCode, boolean b);
}
