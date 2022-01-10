package com.eatsmap.infra.mail;

import com.eatsmap.infra.common.code.CommonCode;
import com.eatsmap.infra.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final AppConfig config;

    public void send(String to, String subject){
        JavaMailSender javaMailSender = config.mailSender();

        javaMailSender.send(new MimeMessagePreparator(){
            public void prepare(MimeMessage mimeMessage) throws MessagingException {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom(CommonCode.MAIL_SENDER.getDesc());
                message.setTo(to);
                message.setSubject(subject);
            }
        });



    }
}
