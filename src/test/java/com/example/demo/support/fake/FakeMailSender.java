package com.example.demo.support.fake;

import com.example.demo.user.service.port.MailSender;
import com.example.demo.user.repository.model.User;

public class FakeMailSender implements MailSender {
    @Override
    public void sendCertificationEmail(String email, long userId, String certificationCode) {
        return;
    }

    @Override
    public String generateCertificationUrl(long userId, String certificationCode) {
        return "test";
    }
}
