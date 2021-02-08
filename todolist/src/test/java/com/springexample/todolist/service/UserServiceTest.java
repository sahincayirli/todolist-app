package com.springexample.todolist.service;

import com.springexample.todolist.model.Todo;
import com.springexample.todolist.model.User;
import com.springexample.todolist.repository.TodoRepository;
import com.springexample.todolist.repository.UserRepository;
import com.springexample.todolist.service.UserService;
import com.springexample.todolist.util.CurrentLoggedInUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    static {
        mockStatic(CurrentLoggedInUser.class);
        when(CurrentLoggedInUser.getCurrentLoggedInUser())
                .thenReturn(new org.springframework.security.core.userdetails.User(
                        "sahin",
                        "sahin123",
                        AuthorityUtils.createAuthorityList()));
    }

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TodoRepository todoRepository;


    @BeforeEach
    void init() {
        userService = new UserService(userRepository, todoRepository);
    }


    @Test
    @DisplayName("should get saved user")
    void saveUserTest() {

        User expected = createUser();
        when(userRepository.save(any(User.class))).thenReturn(expected);
        User actual = userService.saveUser(expected);

        assertThat(actual).usingRecursiveComparison().isNotNull();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    @DisplayName("should add todo to current user and get current user")
    void testAddTodo() {

        User expected = createUser();
        Todo todo = createTodo();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(expected));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);
        when(userRepository.save(any(User.class))).thenReturn(expected);

        User actual = userService.addTodo(todo);

        assertThat(actual).usingRecursiveComparison().isNotNull();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    @DisplayName("should remove todo from current user and return current user")
    void testRemoveTodo() {

        User expected = createUser();
        Todo todo = createTodo();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(expected));
        when(userRepository.save(any(User.class))).thenReturn(expected);

        User actual = userService.removeTodo(todo.getTitle());
        assertThat(actual).usingRecursiveComparison().isNotNull();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("should do some todo and on current user and return current user")
    void testDoTheTodo() {

        User expected = createUser();
        Todo todo = createTodo();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(expected));
        when(todoRepository.findById(anyString())).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        User actual = userService.doTheTodo(todo.getTitle());

        assertThat(actual).usingRecursiveComparison().isNotNull();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("should get list from the current user")
    void testGetTodos() {

        User expected = createUser();

        Todo todo = createTodo();
        expected.addTodo(todo);

        when(userRepository.findById(anyString())).thenReturn(Optional.of(expected));
        when(todoRepository.findById(anyString())).thenReturn(Optional.of(todo));

        List<String> actual = userService.getTodos().stream().map(Todo::getTitle).collect(Collectors.toList());

        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected.getTodoList());

    }

    private User createUser() {
        return User.builder()
                .username("sahin")
                .password("sahin123")
                .todoList(new HashSet<>())
                .build();
    }

    private Todo createTodo() {
        return Todo.builder()
                .title("title")
                .description("desc")
                .done(false)
                .build();
    }

}
