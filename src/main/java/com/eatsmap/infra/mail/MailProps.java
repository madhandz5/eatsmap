package com.eatsmap.infra.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("app.props.mail")
public class MailProps {
    private String host;
    private String username;
    private String password;
    private String protocol;
    private int port;
    private String auth;
    private String enable;
    private String required;
}
