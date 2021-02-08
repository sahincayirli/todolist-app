package com.springexample.todolist.config;

import com.springexample.todolist.model.Todo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.CouchbaseConverter;
import org.springframework.data.couchbase.core.convert.CouchbaseCustomConversions;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;

import java.util.Collections;

@Configuration
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Value("${spring.couchbase.connection-string}")
    private String connectionString;
    @Value("${spring.couchbase.username}")
    private String username;
    @Value("${spring.couchbase.password}")
    private String password;
    @Value("${spring.data.couchbase.bucket-name}")
    private String bucketName;

    @Value("${todolist.is-test}")
    private boolean isTest;

    @Autowired
    private ApplicationContext context;

    @Override
    public String getConnectionString() {
        return connectionString;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getBucketName() {
        return bucketName;
    }

    @SneakyThrows
    @Override
    public void configureRepositoryOperationsMapping(RepositoryOperationsMapping mapping) {
        if (!isTest)
            mapping.mapEntity(Todo.class,getCouchbaseTemplate("todo"));
    }

    private CouchbaseTemplate getCouchbaseTemplate(String bucketName) throws Exception {
        CouchbaseTemplate couchbaseTemplate = new CouchbaseTemplate(
                getCouchbaseClientFactory(bucketName),
                getCouchbaseConverter()
        );


        couchbaseTemplate.setApplicationContext(context);
        return couchbaseTemplate;
    }

    private CouchbaseConverter getCouchbaseConverter() throws Exception {
        return this.mappingCouchbaseConverter(
                this.couchbaseMappingContext(this.customConversions()),
                new CouchbaseCustomConversions(Collections.emptyList())

        );
    }

    private CouchbaseClientFactory getCouchbaseClientFactory(String bucketName) {
        return new SimpleCouchbaseClientFactory(this.couchbaseCluster(this.couchbaseClusterEnvironment()),bucketName,this.getScopeName());
    }
}
