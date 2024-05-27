package com.example.demo.user.infrastructure;

import com.example.demo.user.repository.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender{
    private final JavaMailSender mailSender;

    @Override
    public void sendCertificationEmail(String email, String certificationUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Please certify your email address");
        message.setText("Please click the following link to certify your email address: " + certificationUrl);
        mailSender.send(message);
    }

    @Override
    public String generateCertificationUrl(User user) {
        return "http://localhost:8080/api/users/" + user.getId() + "/verify?certificationCode=" + user.getCertificationCode();
    }
}
