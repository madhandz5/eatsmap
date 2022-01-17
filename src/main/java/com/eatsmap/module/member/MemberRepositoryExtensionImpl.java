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
    public Member guestValidateByEmail(String email) {
        return queryFactory
                .select(member)
                .from(member)
                .where(member.verified.isTrue()
                        , member.exited.isFalse()
                        , member.email.eq(email))
                .fetchFirst();
    }
    //MemberRole = USER 인 사람 중 이메일 검증(LoginValidate)
    @Override
    public Member userValidateByEmail(String email) {
        return queryFactory
                .select(member)
                .from(member)
                .where(member.verified.isTrue()
                        , member.memberRole.eq(MemberRole.USER)
                        , member.exited.isFalse()
                        , member.email.eq(email))
                .fetchOne();
    }

    //LoginValidation


    @Override
    public Member memberValidateByNickname(String nickname) {
        return queryFactory
                .select(member)
                .from(member)
                .where(member.verified.isTrue()
                        , member.exited.isFalse()
                        , member.email.eq(nickname))
                .fetchFirst();
    }



}
