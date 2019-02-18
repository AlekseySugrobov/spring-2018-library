package ru.otus.library.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;
import ru.otus.library.service.GenreService;

@Configuration
public class TestContext {
    @Bean
    public AuthorService authorService() {
        return Mockito.mock(AuthorService.class);
    }

    @Bean
    public GenreService genreService() {
        return Mockito.mock(GenreService.class);
    }

    @Bean
    public BookService bookService() {
        return Mockito.mock(BookService.class);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
