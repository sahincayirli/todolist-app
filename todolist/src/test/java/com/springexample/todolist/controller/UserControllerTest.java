package com.springexample.todolist.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springexample.todolist.model.Todo;
import com.springexample.todolist.model.User;
import com.springexample.todolist.service.UserService;

import lombok.With;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Order(1)
    void testRegisterUser() throws Exception {

        User expected = createUser();

        when(userService.saveUser(any(User.class))).thenReturn(expected);

        MvcResult mvcResult = mockMvc.perform(post("/register")
                .content(mapper.writeValueAsBytes(expected))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        User actual = mapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    @Order(2)
    @WithMockUser
    void testAddTodo() throws Exception {

        Todo todo = createTodo();
        User expected = createUser();
        expected.addTodo(todo);

        when(userService.addTodo(any(Todo.class))).thenReturn(expected);

        MvcResult mvcResult = mockMvc.perform(post("/user/addTodo")
                .content(mapper.writeValueAsBytes(todo))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        User actual = mapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);


        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    @Order(5)
    @WithMockUser
    void testRemoveTodo() throws Exception {

        User expected = createUser();

        when(userService.removeTodo(anyString())).thenReturn(expected);

        MvcResult mvcResult = mockMvc.perform(delete("/user/'title'"))
                .andExpect(status().isOk())
                .andReturn();

        User actual = mapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }


    @Test
    @Order(3)
    @WithMockUser
    void testDoSomeTodo() throws Exception {

        User expected = createUser();
        Todo todo = createTodo();
        todo.setDone(true);
        expected.addTodo(todo);

        when(userService.doTheTodo(anyString())).thenReturn(expected);

        MvcResult mvcResult = mockMvc.perform(put("/user/'title'"))
                .andExpect(status().isOk())
                .andReturn();

        User actual = mapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    @Order(4)
    @WithMockUser
    void testTodoList() throws Exception {


        var expected = Collections.singletonList(createTodo());

        when(userService.getTodos()).thenReturn(expected);

        MvcResult mvcResult = mockMvc.perform(get("/user/todoList"))
                .andExpect(status().isOk())
                .andReturn();

        List<Todo> actual = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);

    }



    private Todo createTodo() {
        return Todo.builder()
                .title("title")
                .description("desc")
                .done(false)
                .build();
    }

    private User createUser() {
        return User.builder()
                .username("sahin")
                .password("sahin123")
                .todoList(new HashSet<>())
                .build();
    }

}
