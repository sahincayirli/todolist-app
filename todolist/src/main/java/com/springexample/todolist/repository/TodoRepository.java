package com.springexample.todolist.repository;

import com.springexample.todolist.model.Todo;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends CouchbaseRepository<Todo,String> {

}
