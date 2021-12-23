package com.eatsmap.infra.utils.kakao;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration("app.props.kakao")
public class KakaoProps {

    private String restApiKey;
    private String javaScriptKey;
    private String redirectUrl;
    private String appAdminKey;
    private String logoutUrl;
}
