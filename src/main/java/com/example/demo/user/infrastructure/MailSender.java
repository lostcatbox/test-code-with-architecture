package com.example.demo.user.infrastructure;

import com.example.demo.user.repository.model.User;

public interface MailSender {
    void sendCertificationEmail(String email, String certificationUrl);

    String generateCertificationUrl(User user);
}
