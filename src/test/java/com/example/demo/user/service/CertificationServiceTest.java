package com.example.demo.user.service;

import com.example.demo.mock.FakeMailSender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CertificationServiceTest {
    @Test
    void send_이메일_전송_성공() {
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(fakeMailSender);

        certificationService.send("kok202@naver.com", 1L, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        Assertions.assertThat(fakeMailSender.email).isEqualTo("kok202@naver.com");
        Assertions.assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
        Assertions.assertThat(fakeMailSender.content).isEqualTo("http://localhost:8080/api/users/" + 1 + "/verify?certificationCode=" + "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    }

}