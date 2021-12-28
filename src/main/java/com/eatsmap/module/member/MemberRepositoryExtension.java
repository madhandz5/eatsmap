package com.eatsmap.module.member;

public interface MemberRepositoryExtension {
    boolean checkEmailVerified(String email);

    Member memberForSignUpByEmail(String email);

    Member memberForSignUpByNickname(String nickname);
}
