package com.example.demo.user.service.port;

import com.example.demo.user.repository.model.User;

public interface MailSender {
    void sendCertificationEmail(String email, long userId, String certificationCode);

    String generateCertificationUrl(long userId, String certificationCode);
}
