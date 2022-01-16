package com.eatsmap.module.member;

public interface MemberRepositoryExtension {
    boolean checkEmailVerified(String email);

    Member guestValidateByEmail(String email);

    Member userValidateByEmail(String email);

    Member memberValidateByNickname(String nickname);

}
