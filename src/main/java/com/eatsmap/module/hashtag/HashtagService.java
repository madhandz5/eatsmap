package com.eatsmap.module.hashtag;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.hashtag.dto.CreateHashtagRequest;
import com.eatsmap.module.hashtag.dto.CreateHashtagResponse;
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
    public CreateHashtagResponse createHashtag(CreateHashtagRequest request) {
        Hashtag hashtag = hashtagRepository.save(Hashtag.createHashtag(request));
        return CreateHashtagResponse.createResponse(hashtag);
    }

    public Hashtag getHashtagByHashtagCode(String hashtagCode) {
        return hashtagRepository.findByHashtagCode(hashtagCode);
    }

}
