package com.springexample.todolist.controller;

import com.springexample.todolist.model.Todo;
import com.springexample.todolist.model.User;
import com.springexample.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PostMapping("addTodo")
    public ResponseEntity<User> addTodo(@RequestBody Todo todo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addTodo(todo));
    }

    @DeleteMapping("{title}")
    public ResponseEntity<User> removeTodo(@PathVariable(value = "title") String title) {
        return ResponseEntity.ok(userService.removeTodo(title));
    }

    @PutMapping("{title}")
    public ResponseEntity<User> doSomeTodo(@PathVariable String title) {
        return ResponseEntity.ok(userService.doTheTodo(title));
    }

    @GetMapping("todoList")
    public ResponseEntity<List<Todo>> getTodos() {
        return ResponseEntity.ok(userService.getTodos());
    }


}
