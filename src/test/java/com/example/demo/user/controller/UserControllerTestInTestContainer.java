package com.example.demo.user.controller;

import com.example.demo.common.domain.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.ResourceNotFoundException;
import com.example.demo.mock.*;
import com.example.demo.user.container.TestContainer;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.service.CertificationService;
import com.example.demo.user.service.port.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class UserControllerTestInTestContainer {

    @Test
    void 사용자는_특정_유저의_정보를_개인정보는_소거된채_전달_받을_수_있다() throws Exception {
        // given
        // when
        // then
        TestContainer testContainer = new TestContainer().getInstance();
        testContainer.getRepository().save(User.builder()
                .id(1L)
                .email("kok404@naver.com")
                .nickname("kok404")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(100L)
                .build());
        ResponseEntity<UserResponse> response = testContainer.getController().getUserById(1);
        //then
        UserResponse result = response.getBody();
        Assertions.assertEquals(result.getId(),1);
        Assertions.assertEquals(result.getEmail(),"kok404@naver.com");
        Assertions.assertEquals(result.getNickname(),"kok404");
        Assertions.assertEquals(result.getStatus(),UserStatus.ACTIVE);

    }

//    @Test
//    void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() throws Exception {
//        // given
//        // when
//        // then
//        org.assertj.core.api.Assertions.assertThatThrownBy(() -> userController.getUserById(11234)).isInstanceOf(ResourceNotFoundException.class);
//    }
//
//    @Test
//    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
//        // given
//        // when
//        // then
//        userRepository.save(User.builder()
//                .id(2L)
//                .email("kok202@naver.com")
//                .nickname("kok202")
//                .address("Seoul")
//                .status(UserStatus.PENDING)
//                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
//                .lastLoginAt(100L)
//                .build());
//
//        userController.verifyEmail(2L, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
//
//        //then
//        Optional<User> byId = userRepository.findById(2L);
//        org.assertj.core.api.Assertions.assertThat(byId.get().getEmail()).isEqualTo("kok202@naver.com");
//        org.assertj.core.api.Assertions.assertThat(byId.get().getStatus()).isEqualTo(UserStatus.ACTIVE);
//    }
//
//    @Test
//    void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() throws Exception {
//        // given
//        // when
//        // then
//
//        userRepository.save(User.builder()
//                .id(2L)
//                .email("kok202@naver.com")
//                .nickname("kok202")
//                .address("Seoul")
//                .status(UserStatus.PENDING)
//                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
//                .lastLoginAt(100L)
//                .build());
//
//
//        org.assertj.core.api.Assertions.assertThatThrownBy(() -> userController.verifyEmail(2L, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaㅊb")).isInstanceOf(CertificationCodeNotMatchedException.class);
//    }
//
//    @Test
//    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() throws Exception {
//        // given
//        // when
//        // then
//
//    }
////
////    @Test
////    void 사용자는_내_정보를_수정할_수_있다() throws Exception {
////        // given
//
//    }

}
