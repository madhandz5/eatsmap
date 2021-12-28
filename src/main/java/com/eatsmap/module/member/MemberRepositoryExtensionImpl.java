package com.eatsmap.module.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import static com.eatsmap.module.member.QMember.member;
import static com.eatsmap.module.verification.QVerification.verification;

public class MemberRepositoryExtensionImpl extends QuerydslRepositorySupport implements MemberRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryExtensionImpl(JPAQueryFactory jpaQueryFactory) {
        super(Member.class);
        this.queryFactory = jpaQueryFactory;
    }


    @Override
    public boolean checkEmailVerified(String email) {
        return queryFactory
                .select(verification.checked)
                .from(verification)
                .leftJoin(member).on(member.id.eq(verification.member.id))
                .where(member.email.eq(email))
                .fetchFirst();
    }

    //SignUpValidate
    @Override
    public Member memberForSignUpByEmail(String email) {
        return queryFactory
                .select(member)
                .from(member)
                .where(member.verified.isTrue()
                        , member.exited.isFalse()
                        , member.email.eq(email))
                .fetchFirst();

    }
    @Override
    public Member memberForSignUpByNickname(String nickname) {
        return queryFactory
                .select(member)
                .from(member)
                .where(member.verified.isTrue()
                        , member.exited.isFalse()
                        , member.email.eq(nickname))
                .fetchFirst();
    }



}
