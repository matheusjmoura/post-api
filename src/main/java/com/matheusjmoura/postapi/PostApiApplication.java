package com.matheusjmoura.postapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableMongoRepositories
@EnableTransactionManagement
public class PostApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostApiApplication.class, args);
    }

}
