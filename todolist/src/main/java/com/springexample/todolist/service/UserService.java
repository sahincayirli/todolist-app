package com.springexample.todolist.service;

import com.springexample.todolist.model.Todo;
import com.springexample.todolist.model.User;
import com.springexample.todolist.repository.TodoRepository;
import com.springexample.todolist.repository.UserRepository;
import com.springexample.todolist.util.CurrentLoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User addTodo(Todo todo) {

        var user = getCurrentLoggedInUser();
        user.addTodo(todo);
        todoRepository.save(todo);
        return userRepository.save(user);
    }

    public User removeTodo(String title) {
        var user = getCurrentLoggedInUser();
        user.removeTodo(title);
        todoRepository.deleteById(title);
        return userRepository.save(user);
    }

    public User doTheTodo(String title) {
        var user = getCurrentLoggedInUser();
        Todo todo = todoRepository.findById(title).get();
        todo.setDone(true);
        todoRepository.save(todo);
        return user;
    }

    public List<Todo> getTodos() {
        var user = getCurrentLoggedInUser();
        return user.getTodoList().stream().map(todoRepository::findById).map(Optional::get).collect(Collectors.toList());
    }

    public User getCurrentLoggedInUser() {
        var uname = CurrentLoggedInUser.getCurrentLoggedInUser();
        var user = userRepository.findById(uname.getUsername()).get();
        return user;
    }
}
