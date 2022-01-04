package com.eatsmap.module.hashtag;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
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
    public Hashtag createHashtag(List<String> tagList) {
        Hashtag hashtag = Hashtag.createHashtag(tagList);
        if (hashtag.getMd01()==0 && hashtag.getMd02()==0 && hashtag.getMd03()==0 && hashtag.getMd04()==0 && hashtag.getMd05()==0 && hashtag.getMd06()==0
                && hashtag.getPr01()==0 && hashtag.getPr02()==0 && hashtag.getPr03()==0 && hashtag.getPr04()==0 && hashtag.getPr05()==0) {
            throw new CommonException(ErrorCode.HASHTAG_IS_NOT_EXISTS);
        }
        return hashtag;
    }

}
