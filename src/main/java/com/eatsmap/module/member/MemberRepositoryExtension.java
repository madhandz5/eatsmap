package com.eatsmap.module.member;

public interface MemberRepositoryExtension {
    boolean checkEmailVerified(String email);

    Member memberValidateByEmail(String email);

    Member memberValidateByNickname(String nickname);
}
