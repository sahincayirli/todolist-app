package com.springexample.todolist.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class User {

    @Id
    @Field
    private String username;
    @Field
    private String password;
    @Field
    private Set<String> todoList = new HashSet<>();

    public void addTodo(Todo todo) {
        todoList.add(todo.getTitle());
    }

    public void removeTodo(String title) {
        todoList.remove(title);
    }

}
