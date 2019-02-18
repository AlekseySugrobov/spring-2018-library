package ru.otus.library.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.LibraryDataException;
import ru.otus.library.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity listAuthors() {
        List<Author> authors = authorService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity getAuthor(@PathVariable String id) {
        try {
            Author author = authorService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(author);
        } catch (LibraryDataException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/edit")
    public ResponseEntity editAuthor(@RequestBody Author author) {
        authorService.save(author);
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAuthor(@PathVariable String id) {
        try {
            authorService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (LibraryDataException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
