package com.project.trendora.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendMail(String to,String subject,String body){
        log.info("Email sending started | recipient={}", to);
        try{
            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            javaMailSender.send(message);
            log.info("Email sent successfully | recipient={}", to);

        }catch (MailException e){
            log.error("Email sending failed | recipient={} | error={}", to, e.getMessage(), e);

        }
    }
}
