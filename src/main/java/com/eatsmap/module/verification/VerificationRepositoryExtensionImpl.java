package com.eatsmap.module.verification;

import com.eatsmap.module.member.QMember;
import com.eatsmap.module.verification.dto.VerificationResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.annotations.QueryHints;
import org.hibernate.criterion.Projection;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;

import static com.eatsmap.module.verification.QVerification.verification;

public class VerificationRepositoryExtensionImpl extends QuerydslRepositorySupport implements VerificationRepositoryExtension {

    private final JPAQueryFactory queryFactory;

    public VerificationRepositoryExtensionImpl(JPAQueryFactory queryFactory) {
        super(Verification.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Verification searchVerification(String key) {
        return queryFactory.selectFrom(verification)
                .where(
                        verification.key.eq(key),
                        verification.checked.isFalse()
                ).fetchFirst();
    }

    @Override
    public VerificationResponse searchVerification(String key, LocalDateTime dateTime) {
        return queryFactory
                .select(Projections.constructor(VerificationResponse.class,
                                verification.key,
                                verification.expiredAt,
                                verification.member
                        )
                )
                .from(verification)
                .join(verification.member, QMember.member)
                .where(
                        verification.key.eq(key),
                        verification.checked.isFalse(),
                        verification.expiredAt.after(dateTime))
                .setHint(QueryHints.READ_ONLY, true)
                .fetchFirst();
    }
}
