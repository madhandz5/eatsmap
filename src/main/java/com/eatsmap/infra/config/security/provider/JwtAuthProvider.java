package com.eatsmap.infra.config.security.provider;

import com.eatsmap.infra.common.code.ErrorCode;
import com.eatsmap.infra.exception.CommonException;
import com.eatsmap.module.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        final String targetEmail = String.valueOf(authentication.getCredentials());
        return memberService.loadUserByUsername(targetEmail);   //principal 등록
    }

    // retrieveUser 이후 로직 실행 : authentication(입력받은 정보)와 userDetails(DB정보) 비교
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        if(authentication.getCredentials() == null){
            throw new CommonException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        if(!passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())){
            throw new CommonException(ErrorCode.JWT_EXCEPTION_FAIL);
        }

    }


}
