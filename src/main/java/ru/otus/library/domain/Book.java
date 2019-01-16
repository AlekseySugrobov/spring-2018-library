package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book-generator")
    @SequenceGenerator(name = "book-generator", sequenceName = "book_seq", allocationSize = 1)
    private Long id;
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private Author author;
    @OneToOne(cascade = CascadeType.ALL)
    private Genre genre;

    public Book(String name, Genre genre, Author author) {
        this.name = name;
        this.genre = genre;
        this.author = author;
    }
}
