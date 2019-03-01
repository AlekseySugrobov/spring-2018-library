package ru.otus.library.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.otus.library.Library;
import ru.otus.library.config.TestContext;
import ru.otus.library.domain.Author;
import ru.otus.library.service.AuthorService;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DisplayName("Тесты AuthorController")
@ContextConfiguration(classes = {TestContext.class, Library.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuthorControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Получение списка авторов")
    public void testGetAuthorsList() {
        webTestClient.get().uri("/authors")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Author.class);
    }

    @Test
    @DisplayName("Получение автора по ИД")
    public void getAuthor() {
        Author author = authorService.save(new Author("name")).block();
        webTestClient.get()
                .uri("/authors/{id}", Collections.singletonMap("id", author.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> assertThat(response.getResponseBody()).isNotNull());

    }

    @Test
    @DisplayName("Создание автора")
    public void editAuthor() {
        Author author = new Author("name1");
        webTestClient
                .post()
                .uri("/authors/edit")
                .body(Mono.just(author), Author.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    @DisplayName("Удаление автора")
    public void deleteAuthor() {
        Author author = authorService.save(new Author("name")).block();
        webTestClient
                .delete()
                .uri("/authors/{id}", Collections.singletonMap("id", author.getId()))
                .exchange();
        webTestClient
                .get()
                .uri("/authors/{id}", Collections.singletonMap("id", author.getId()))
                .exchange()
                .expectStatus().isNotFound();
    }
}