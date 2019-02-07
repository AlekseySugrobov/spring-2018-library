package ru.otus.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.otus.library.processors.CascadeSaveMongoEventListener;

@SpringBootApplication
public class Library {

    @Bean
    public CascadeSaveMongoEventListener cascadeSaveMongoEventListener() {
        return new CascadeSaveMongoEventListener();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Library.class, args);
    }
}
