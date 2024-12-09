package com.example.booking.service;

import lombok.AllArgsConstructor;import lombok.Builder;
import lombok.Data;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class EmailService {

    @Data
    @Builder
    public static class EmailContainer{
        private String to;
        private String subject;
        private String text;
    }

    private final JavaMailSender mailSender;

    public void sendNewEmail(EmailContainer emailContainer) {
        new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailContainer.getTo());
            message.setSubject(emailContainer.getSubject());
            message.setText(emailContainer.getText());
            mailSender.send(message);
        }).start();
    }
}