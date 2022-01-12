package com.eatsmap.module.member;

import com.eatsmap.module.member.dto.ModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        byte[] fileToBinary = null;

        Member modifiedMember = null;
        if (member.isPresent()) {
            Member findUser = member.get();
            findUser.modifyMember(member.get(), request, fileToBinary );
            modifiedMember = memberRepository.save(findUser);
        }

        //WHEN
        Member memberByEmail = memberRepository.findByEmail(member.get().getEmail());

        //THEN
        assertEquals(modifiedMember.getEmail(), memberByEmail.getEmail());
    }
}
