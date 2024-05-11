package com.example.demo.user.service.domain;

import com.example.demo.common.domain.CertificationCodeNotMatchedException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class UserTest {

    @Test
    void User_는_UserCreate_로_새로운_객체생성가능하(){
        UserCreate userCreate = UserCreate.builder()
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .build();
        User user = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));

        Assertions.assertThat(user.getEmail()).isEqualTo(userCreate.getEmail());
        Assertions.assertThat(user.getAddress()).isEqualTo(userCreate.getAddress());
        Assertions.assertThat(user.getNickname()).isEqualTo(userCreate.getNickname());
        Assertions.assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        Assertions.assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    }

    @Test
    void User는_UPDATE가_가능하다(){

        User targetUser = User.builder()
                .id(1L)
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa").random())
                .lastLoginAt(new TestClockHolder(1234).millis())
                .build();

        UserUpdate userUpdate = UserUpdate.builder()
                .address("BUSAN")
                .nickname("ysw0623")
                .build();
        User updatedUser = targetUser.update(userUpdate, new TestClockHolder(23456L));

        Assertions.assertThat(updatedUser.getLastLoginAt()).isEqualTo(new TestClockHolder(23456L).millis());
        Assertions.assertThat(updatedUser.getAddress()).isEqualTo("BUSAN");
        Assertions.assertThat(updatedUser.getNickname()).isEqualTo("ysw0623");
        Assertions.assertThat(updatedUser.getEmail()).isEqualTo("kok202@naver.com");
        Assertions.assertThat(updatedUser.getStatus()).isEqualTo(UserStatus.PENDING);
        Assertions.assertThat(updatedUser.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

    @Test
    void 로그인시_로그인정보_업데이트(){
        User targetUser = User.builder()
                .id(1L)
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa").random())
                .lastLoginAt(new TestClockHolder(1234).millis())
                .build();

        User loginedUser = targetUser.login(new TestClockHolder(2345));

        Assertions.assertThat(loginedUser.getLastLoginAt()).isEqualTo(new TestClockHolder(2345).millis());
    }

    @Test
    void 이메일인증시_statusACTIVE로_바뀐다() {
        User targetUser = User.builder()
                .id(1L)
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa").random())
                .lastLoginAt(new TestClockHolder(1234).millis())
                .build();

        User verifiedUser = targetUser.verifyEmail("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        Assertions.assertThat(verifiedUser.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
    @Test
    void FAIL_잘못된_인증코드시_에러코드던진다() {
        User targetUser = User.builder()
                .id(1L)
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa").random())
                .lastLoginAt(new TestClockHolder(1234).millis())
                .build();


        Assertions.assertThatThrownBy(() -> targetUser.verifyEmail("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaawaab"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }



}
