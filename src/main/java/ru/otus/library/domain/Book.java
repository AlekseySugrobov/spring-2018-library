package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.library.annotations.CascadeSave;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private String name;
    @DBRef
    @CascadeSave
    private Author author;
    @DBRef
    @CascadeSave
    private Genre genre;

    public Book(String name, Genre genre, Author author) {
        this.name = name;
        this.genre = genre;
        this.author = author;
    }

    public Book() {
        this.genre = new Genre();
        this.author = new Author();
    }
}
