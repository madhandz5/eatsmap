package com.eatsmap.infra.config.security.provider;

import com.eatsmap.module.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthProvider extends AbstractUserDetailsAuthenticationProvider {

    private final MemberService memberService;

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        final String targetEmail = String.valueOf(authentication.getCredentials());
        return memberService.loadUserByUsername(targetEmail);
    }

    // retrieveUser 이후 로직 실행
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }


}
