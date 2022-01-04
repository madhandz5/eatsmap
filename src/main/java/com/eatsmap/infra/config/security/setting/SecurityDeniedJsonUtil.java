package com.eatsmap.infra.config.security.setting;

import com.eatsmap.infra.common.code.ErrorCode;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Component
public class SecurityDeniedJsonUtil {
    public void setAccessDeniedResponse(HttpServletResponse response) throws IOException {
        int status = HttpServletResponse.SC_FORBIDDEN;

        setDefaultErrorResponse(response, status);
        createRestResponse(response, ErrorCode.ACCESS_DENIED.getErrorString());
    }

    public void setAuthenticationFailedResponse(HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        int status = HttpServletResponse.SC_UNAUTHORIZED;

        setDefaultErrorResponse(response, status);
        createRestResponse(response, authenticationException.getMessage());
    }

    private void setDefaultErrorResponse(HttpServletResponse response, int status) {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    }

    private void createRestResponse(HttpServletResponse servletResponse, String message) throws IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("success", false);
            jsonObject.put("data", new JSONObject(message));
        } catch (JSONException ignored) {
        } finally {
            PrintWriter writer = servletResponse.getWriter();
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();
        }
    }

}
