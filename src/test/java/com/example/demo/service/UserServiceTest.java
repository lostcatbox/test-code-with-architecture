package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.UserStatus;
import com.example.demo.repository.UserEntity;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void getByEmail_은_ACTIVE인_상태인_유저를_찾아올_수_있다(){
        //given
        String email = "테스트@naver.com";

        //when
        UserEntity result = userService.getByEmail(email);

        //then
        assertThat(result.getNickname()).isEqualTo("테스트아이디");
    }
    @Test
    void getByEmail_은_PENDING인_상태인_유저를_찾아올_수_없다(){
        //given
        String email = "테스트@naver.com2";

        //when
        assertThatThrownBy(() -> {
            UserEntity byEmail = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }
    @Test
    void getById_은_ACTIVE인_상태인_유저를_찾아올_수_있다(){
        int id = 1;

        UserEntity result = userService.getById(id);
        assertThat(result.getNickname()).isEqualTo("테스트아이디");
    }

    @Test
    void getById_은_PENDING인_상태인_유저를_찾아올_수_없다(){
        int id = 2;

        assertThatThrownBy(() -> {
            UserEntity result = userService.getById(id);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

}