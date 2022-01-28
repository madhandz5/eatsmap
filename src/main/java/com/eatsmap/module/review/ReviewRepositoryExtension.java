package com.eatsmap.module.review;

import com.eatsmap.module.member.Member;

import java.util.List;

public interface ReviewRepositoryExtension {

    List<Review> findTimeline(Member member);

}
