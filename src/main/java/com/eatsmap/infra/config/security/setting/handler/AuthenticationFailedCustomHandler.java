package com.eatsmap.infra.config.security.setting.handler;

import com.eatsmap.infra.config.security.setting.SecurityDeniedJsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFailedCustomHandler implements AuthenticationFailureHandler {

    private final SecurityDeniedJsonUtil securityDeniedJsonUtil;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        securityDeniedJsonUtil.setAuthenticationFailedResponse(response, exception);
    }
}
