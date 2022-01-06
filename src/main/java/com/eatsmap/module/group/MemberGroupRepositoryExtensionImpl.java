package com.eatsmap.module.group;

import com.eatsmap.module.member.Member;
import com.querydsl.core.types.Projections;
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
    public List<MemberGroup> getAllMemberGroup(Member member){
        return queryFactory
                .select(Projections.fields(MemberGroup.class,
                        memberGroup.id,
                        memberGroup.createdBy,
                        memberGroup.groupName,
                        memberGroup.groupMembers))
                .from(memberGroupHistory)
                .join(memberGroup.groupMembers, memberGroupHistory)
                .where(memberGroupHistory.member.eq(member))
                .fetch();
    }

}
