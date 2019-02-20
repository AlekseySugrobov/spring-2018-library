package ru.otus.library.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.LibraryDataException;
import ru.otus.library.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity listGenres() {
        List<Genre> genres = genreService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity getGenre(@PathVariable String id) {
        try {
            Genre genre = genreService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(genre);
        } catch (LibraryDataException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/edit")
    public ResponseEntity editGenre(@RequestBody Genre genre) {
        try {
            genreService.save(genre);
            return ResponseEntity.status(HttpStatus.OK).body(genre);
        } catch (LibraryDataException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
