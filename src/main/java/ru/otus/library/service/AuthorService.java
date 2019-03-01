package ru.otus.library.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Author;

public interface AuthorService {
    Flux<Author> findAll();

    Mono<Author> findById(String id);

    Mono<Author> save(Author author);

    Mono<Void> delete(String id);
}
