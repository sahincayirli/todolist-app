package com.springexample.todolist.repository;

import com.springexample.todolist.model.Todo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TodoRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;


    @BeforeEach
    void setup() {
        todoRepository.deleteAll();
    }

    @Test
    void testSaveTodo() {

        Todo expected = createTodo();

        Todo actual = todoRepository.save(expected);

        assertThat(actual).isNotNull();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void testFindTodoById() {

        Todo expected = createTodo();

        todoRepository.save(expected);

        Todo actual = todoRepository.findById(expected.getTitle()).get();

        assertThat(actual).isNotNull();
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }

    @Test
    void testDeleteTodoById() {

        Todo todo = createTodo();

        long expected = 0;

        todoRepository.save(todo);

        todoRepository.deleteById(todo.getTitle());

        long actual = todoRepository.count();

        assertThat(actual).isEqualTo(expected);

    }


    private Todo createTodo() {
        return Todo.builder()
                .title("title")
                .description("desv")
                .done(false)
                .build();

    }
}
