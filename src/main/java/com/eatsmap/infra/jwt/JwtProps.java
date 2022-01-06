package com.eatsmap.infra.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("app.props.jwt")
public class JwtProps {
    private String secretkey;
    private String issuer;
    private String claimId;
}
