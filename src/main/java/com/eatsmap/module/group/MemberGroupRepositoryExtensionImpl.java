package com.eatsmap.module.group;

import com.eatsmap.module.group.dto.MemberGroupDTO;
import com.eatsmap.module.group.dto.QSimpleMemberGroupDTO;
import com.eatsmap.module.group.dto.SimpleMemberGroupDTO;
import com.eatsmap.module.member.Member;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.eatsmap.module.group.QMemberGroup.memberGroup;
import static com.eatsmap.module.groupMemberHistory.QMemberGroupHistory.memberGroupHistory;

public class MemberGroupRepositoryExtensionImpl extends QuerydslRepositorySupport implements MemberGroupRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public MemberGroupRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(MemberGroup.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<SimpleMemberGroupDTO> getAllMemberGroup(Member member){
        return queryFactory
                .select(new QSimpleMemberGroupDTO(memberGroup.id,
                                            memberGroup.createdBy,
                                            memberGroup.groupName,
                                            memberGroup.regDate,
                                            memberGroup.totalGroupMemberCnt,
                                            memberGroup.joinedGroupMemberCnt))
                .from(memberGroupHistory)
                .join(memberGroupHistory.memberGroup, memberGroup)
                .where(memberGroupHistory.member.id.eq(member.getId()))
                .fetch();
    }

    @Override
    public List<String> getAllGroupMemberNickname() {
        return queryFactory
                .select(memberGroupHistory.member.nickname)
                .from(memberGroupHistory)
                .fetch();

    }

    @Override
    public List<Tuple> getAllGroupMembers() {
        return queryFactory
                .select(memberGroupHistory.member.id
                        , memberGroupHistory.member.nickname
                        , memberGroup.id
                        , memberGroup.groupName)
                .from(memberGroupHistory)
                .leftJoin(memberGroupHistory.memberGroup, memberGroup)
                .fetch();
    }

    @Override
    public List<Tuple> getAllGroup() {
        return queryFactory
                .select(memberGroup.id
                        , memberGroup.groupName
                        , memberGroupHistory.member.id
                        , memberGroupHistory.member.nickname)
                .from(memberGroup)
                .innerJoin(memberGroupHistory).on(memberGroup.groupMembers.contains(memberGroupHistory))
                .fetch();
    }

}
