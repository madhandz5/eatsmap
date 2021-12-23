package com.eatsmap.module.hashtag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    @Transactional
    public Hashtag createHashtag(Hashtag hashtag) {
        return hashtagRepository.saveAndFlush(hashtag);
    }
}
