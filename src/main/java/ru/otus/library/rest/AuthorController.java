package ru.otus.library.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.domain.Author;
import ru.otus.library.service.AuthorService;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public Flux<Author> listAuthors() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Author>> getAuthor(@PathVariable String id) {
        return Mono
                .just(id)
                .flatMap(this.authorService::findById)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/edit")
    public Mono<ResponseEntity<Author>> editAuthor(@RequestBody Author author) {
        return Mono
                .just(author)
                .flatMap(this.authorService::save)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAuthor(@PathVariable String id) {
        return Mono
                .just(id)
                .flatMap(this.authorService::delete)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
