package ru.otus.library.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Book;
import ru.otus.library.exception.LibraryDataException;
import ru.otus.library.service.BookService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity listBooks() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity getBook(@PathVariable String id) {
        try {
            Book book = bookService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        } catch (LibraryDataException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/edit")
    public ResponseEntity editBook(@Valid @RequestBody Book book) {
        try {
            bookService.save(book);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        } catch (LibraryDataException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
