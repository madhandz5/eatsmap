package com.eatsmap.infra.config.security.setting.handler;

import com.eatsmap.infra.config.security.setting.SecurityDeniedJsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AccessDeniedCustomHandler implements AccessDeniedHandler {

    private final SecurityDeniedJsonUtil securityDeniedJsonUtil;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        securityDeniedJsonUtil.setAccessDeniedResponse(response);
    }
}
