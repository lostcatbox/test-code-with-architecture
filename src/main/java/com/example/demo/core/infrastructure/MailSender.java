package com.example.demo.core.infrastructure;

import com.example.demo.user.repository.entity.UserEntity;
import com.example.demo.user.service.model.User;

public interface MailSender {
    void sendCertificationEmail(String email, String certificationUrl);

    String generateCertificationUrl(User user);
}
