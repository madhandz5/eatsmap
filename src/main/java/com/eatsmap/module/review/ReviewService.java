package com.eatsmap.module.review;

import com.eatsmap.module.member.Member;
import com.eatsmap.module.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;


    @Transactional
    public void createReview() {
        Member member = memberRepository.findByEmail("test@test.co.kr");
        Review review = new Review();
        review.setMember(member);

        reviewRepository.save(review);
    }
}
