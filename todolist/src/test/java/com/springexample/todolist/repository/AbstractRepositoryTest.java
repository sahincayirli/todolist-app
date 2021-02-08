package com.springexample.todolist.repository;

import org.junit.ClassRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.couchbase.BucketDefinition;
import org.testcontainers.couchbase.CouchbaseContainer;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractRepositoryTest {

    public static BucketDefinition bucket = new BucketDefinition("test");

    public static CouchbaseContainer container = new CouchbaseContainer("couchbase/server:latest")
            .withBucket(bucket);


    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.couchbase.connection-string",container::getConnectionString);
        registry.add("spring.couchbase.username",container::getUsername);
        registry.add("spring.couchbase.password",container::getPassword);
        registry.add("spring.data.couchbase.bucket-name",bucket::getName);
        registry.add("todolist.is-test", () -> true);
    }

    static {
        container.start();
    }

}
