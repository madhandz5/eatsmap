package com.eatsmap.module.member;

import static org.junit.jupiter.api.Assertions.*;
import com.eatsmap.module.member.dto.ModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("token으로 회원 조회 테스트")
    void findMemberByTokenTest() {
        //GIVEN
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5dWppbkBnb29nbGUuY29tIiwiaWF0IjoxNjQwMTg1ODU1LCJleHAiOjE2NDAyMjE4MzF9.dAAXFtKaVxBkeoMEIDai4cO4VSNNBS2Tljn8SaVcp7g";
        Optional<Member> member = memberRepository.findById(1001L);

        ModifyRequest request = new ModifyRequest();
        request.setNickname("nick");
        request.setPassword("nick123@");
        request.setPasswordConfirm("nick123@");

        Member modifiedMember = null;
        if (member.isPresent()) {
            Member findUser = member.get();
            findUser.modifyMember(request);
            modifiedMember = memberRepository.save(findUser);
        }

        //WHEN
        Member memberByToken = memberRepository.findMemberByJwtToken(token);

        //THEN
        assertEquals(modifiedMember.getEmail(), memberByToken.getEmail());
    }
}
