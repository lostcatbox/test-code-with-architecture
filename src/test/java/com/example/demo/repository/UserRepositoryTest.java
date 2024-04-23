package com.example.demo.repository;

import com.example.demo.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = true)
@TestPropertySource("classpath:test-application.properties")
@Sql("/sql/user-repository-test-data.sql")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByIdAndStatus_로_과거데이터_찾아올수있다(){

        Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.ACTIVE);

        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByIdAndStatus_로_데이터가_없다면_Optional_empty(){

        Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.PENDING);

        //then
        assertThat(result.isEmpty()).isTrue();
    }


    @Test
    void findByEmailAndStatus_로_과거데이터_찾아올수있다(){
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("테스트@naver.com", UserStatus.ACTIVE);

        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByEmailAndStatus_로_데이터가_없다면_Optional_empty(){

        Optional<UserEntity> result = userRepository.findByEmailAndStatus("테스트@naver.com", UserStatus.PENDING);

        //then
        assertThat(result.isEmpty()).isTrue();
    }
}
