package com.springexample.todolist.repository;

import com.springexample.todolist.model.Todo;
import com.springexample.todolist.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testSaveUser() {

        User expected = createUser();

        User actual = userRepository.save(expected);

        assertThat(actual).isNotNull();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void testFindUserById() {

        User expected = createUser();

        userRepository.save(expected);
        User actual = userRepository.findById(expected.getUsername()).get();

        assertThat(actual).isNotNull();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }


    private User createUser() {
        return User.builder()
                .username("sahin")
                .password("sahin123")
                .todoList(new HashSet<>())
                .build();
    }


}
