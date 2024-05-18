package com.example.demo.user.controller;

import com.example.demo.mock.FackUserRepository;
import com.example.demo.mock.FakeUserService;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.infrastructure.UserEntity;
import com.example.demo.user.infrastructure.UserJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private UserController userController = new UserController(new FakeUserService(new FackUserRepository()));

    @Test
    void 사용자는_특정_유저의_정보를_개인정보는_소거된채_전달_받을_수_있다() throws Exception {
        // given
        // when
        // then
        ResponseEntity<UserResponse> response = userController.getUserById(1);
        //then
        UserResponse result = response.getBody();
        Assertions.assertEquals(result.getId(),1);
        Assertions.assertEquals(result.getEmail(),"kok404@naver.com");
        Assertions.assertEquals(result.getNickname(),"kok404");
        Assertions.assertEquals(result.getStatus(),UserStatus.ACTIVE);

    }

    @Test
    void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() throws Exception {
        // given
        // when
        // then
        ResponseEntity<UserResponse> response = userController.getUserById(11234);
        mockMvc.perform(get("/api/users/123456789"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Users에서 ID 123456789를 찾을 수 없습니다."));
    }

//    @Test
//    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
//        // given
//        // when
//        // then
//        mockMvc.perform(
//            get("/api/users/2/verify")
//                .queryParam("certificationCode", "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab"))
//            .andExpect(status().isFound());
//        UserEntity userEntity = userJpaRepository.findById(1L).get();
//        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
//    }
//
//    @Test
//    void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() throws Exception {
//        // given
//        // when
//        // then
//        mockMvc.perform(
//            get("/api/users/2/verify")
//                .queryParam("certificationCode", "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac"))
//            .andExpect(status().isForbidden());
//    }
//
//    @Test
//    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() throws Exception {
//        // given
//        // when
//        // then
//        mockMvc.perform(
//            get("/api/users/me")
//                .header("EMAIL", "kok202@naver.com"))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.id").value(1))
//            .andExpect(jsonPath("$.email").value("kok202@naver.com"))
//            .andExpect(jsonPath("$.nickname").value("kok202"))
//            .andExpect(jsonPath("$.address").value("Seoul"))
//            .andExpect(jsonPath("$.status").value("ACTIVE"));
//    }
//
//    @Test
//    void 사용자는_내_정보를_수정할_수_있다() throws Exception {
//        // given
//        UserUpdate userUpdate = UserUpdate.builder()
//            .nickname("kok202-n")
//            .address("Pangyo")
//            .build();
//
//        // when
//        // then
//        mockMvc.perform(
//            put("/api/users/me")
//                .header("EMAIL", "kok202@naver.com")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userUpdate)))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.id").value(1))
//            .andExpect(jsonPath("$.email").value("kok202@naver.com"))
//            .andExpect(jsonPath("$.nickname").value("kok202-n"))
//            .andExpect(jsonPath("$.address").value("Pangyo"))
//            .andExpect(jsonPath("$.status").value("ACTIVE"));
//    }

}
