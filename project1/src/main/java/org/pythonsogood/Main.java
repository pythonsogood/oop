package org.pythonsogood;

import org.pythonsogood.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
public class Main extends SpringBootServletInitializer {
    public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
    }
}