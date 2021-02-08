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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Todo {

    @Id
    @Field
    private String title;
    @Field
    private String description;
    @Field
    private boolean done;

}
