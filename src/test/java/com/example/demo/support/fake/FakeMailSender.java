package com.example.demo.support.fake;

import com.example.demo.user.service.port.MailSender;
import com.example.demo.user.repository.model.User;

public class FakeMailSender implements MailSender {
    @Override
    public void sendCertificationEmail(String email, String certificationUrl) {
    }

    @Override
    public String generateCertificationUrl(User user) {
        return "test";
    }
}
