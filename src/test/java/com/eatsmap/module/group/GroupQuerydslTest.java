package com.eatsmap.module.group;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@SpringBootTest
public class GroupQuerydslTest {

    @Autowired
    private EntityManager em;

    JPAQueryFactory queryFactory;

    @Autowired
    private MemberGroupRepository memberGroupRepository;

    @BeforeEach
    public void before(){
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    @DisplayName("히스토리 내부 Member로부터 nickname 조회 테스트")
    public void getGroupMember(){
        List<String> groupMemberNicknames = memberGroupRepository.getAllGroupMemberNickname();
        assertEquals(List.of("nick1","nic"), groupMemberNicknames);
    }

    @Test
    @DisplayName("history 기준 조인, 초대된 유저 & 그룹")
    public void getGroupHistory(){
        log.info("groupHistory : {}", memberGroupRepository.getAllGroupMembers());
    }

    @Test
    @DisplayName("memberGroup 기준 조인 테스트")
    public void getAllGroup(){
        log.info("group 기준: {}",memberGroupRepository.getAllGroup());
    }
}
