package com.eatsmap.module.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import javax.persistence.EntityManager;

import static com.eatsmap.module.member.QMember.member;

@SpringBootTest
public class MemberQuerydslTest {

    @Autowired
    private EntityManager em;

    JPAQueryFactory queryFactory;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void before(){
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void test(){
        //GIVEN
        Member member = memberRepository.findById(1001L).get();
        //WHEN
        QMember m = new QMember("m");
        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.email.eq("mem1@gmail.com"))
                .fetchOne();
        //THEN
        assertEquals(member.getEmail(), findMember.getEmail());
    }

    @Test
    public void loginByPasswordQuerydsl(){
        //GIVEN
        Member member = memberRepository.findById(1002L).get();
        //WHEN
        QMember m = new QMember("m");
        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.memberRole.eq(MemberRole.USER)
                        , m.verified.isTrue()
                        , m.email.eq("mem2@gmail.com"))
                .fetchOne();
        //THEN
        assertEquals(member.getEmail(), findMember.getEmail());
    }
}
