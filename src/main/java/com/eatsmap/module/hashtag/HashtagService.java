package com.eatsmap.module.hashtag;

import com.eatsmap.infra.common.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    @Transactional
    public Hashtag createHashtag(List<String> hashtags) {
        return Hashtag.createHashtag(hashtags);
    }

}
