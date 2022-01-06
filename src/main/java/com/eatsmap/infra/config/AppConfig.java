package com.eatsmap.infra.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.eatsmap.infra.jwt.JwtProps;
import com.eatsmap.infra.mail.MailProps;
import com.eatsmap.infra.utils.kakao.KakaoAccountInfoDeserializer;
import com.eatsmap.infra.utils.kakao.KakaoAccountInfoDto;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.util.http.fileupload.util.Closeable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final MailProps mailProps;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public JWTVerifier jwtVerifier(JwtProps jwtProps) {
        return JWT
                .require(Algorithm.HMAC256(jwtProps.getSecretkey()))
                .withIssuer(jwtProps.getIssuer())
                .build();
    }

    @Bean
    public RestTemplate getCustomRestTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(2000);
        factory.setReadTimeout(5000);

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(20)
                .build();
        factory.setHttpClient(httpClient);
        return new RestTemplate(factory);
    }

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProps.getHost());
        mailSender.setProtocol(mailProps.getProtocol());
        mailSender.setPort(mailProps.getPort());
        mailSender.setUsername(mailProps.getUsername());
        mailSender.setPassword(mailProps.getPassword());
        mailSender.setDefaultEncoding("utf-8");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", mailProps.getAuth());
        properties.put("mail.smtp.ssl.enable", mailProps.getEnable());
        properties.put("mail.smtp.ssl.trust", mailProps.getHost());
        properties.put("mail.smtp.starttls.enable", mailProps.getEnable());
        properties.put("mail.smtp.starttls.required", mailProps.getRequired());
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }

    // ObjectMapper를 사용하여 형변환이 이루어 질때
    // 해당 모듈을 통해 원하는 모양으로 변경
    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 특정 모양으로 변경하기 위해 설정
        SimpleModule module = new SimpleModule();
        module.addSerializer(BindingResult.class, new BindingResultSerializer());
        module.addDeserializer(KakaoAccountInfoDto.class, new KakaoAccountInfoDeserializer());

        objectMapper.registerModule(module);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }


    private static class BindingResultSerializer extends JsonSerializer<BindingResult> {
        @Override
        public void serialize(BindingResult result, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartArray();
            List<FieldError> fieldErrorGroup = result.getFieldErrors();
            for (FieldError error : fieldErrorGroup) {
                gen.writeStartObject();
                gen.writeStringField(error.getField(), error.getDefaultMessage());
                gen.writeEndObject();
            }
            gen.writeEndArray();
        }
    }
}
