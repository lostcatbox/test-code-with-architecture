package com.example.demo.repository;

import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.impl.UserJpaRepository;
import com.example.demo.domain.user.enums.UserStatus;
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
public class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void findByIdAndStatus_로_과거데이터_찾아올수있다(){

        Optional<User> result = userJpaRepository.findByIdAndStatus(1, UserStatus.ACTIVE);

        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByIdAndStatus_로_데이터가_없다면_Optional_empty(){

        Optional<User> result = userJpaRepository.findByIdAndStatus(1, UserStatus.PENDING);

        //then
        assertThat(result.isEmpty()).isTrue();
    }


    @Test
    void findByEmailAndStatus_로_과거데이터_찾아올수있다(){
        Optional<User> result = userJpaRepository.findByEmailAndStatus("테스트@naver.com", UserStatus.ACTIVE);

        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByEmailAndStatus_로_데이터가_없다면_Optional_empty(){

        Optional<User> result = userJpaRepository.findByEmailAndStatus("테스트@naver.com", UserStatus.PENDING);

        //then
        assertThat(result.isEmpty()).isTrue();
    }
}
