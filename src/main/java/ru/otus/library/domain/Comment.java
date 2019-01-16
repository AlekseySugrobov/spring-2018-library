package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment-generator")
    @SequenceGenerator(name = "comment-generator", sequenceName = "comment_seq", allocationSize = 1)
    private Long id;
    private String text;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;

    public Comment(String text) {
        this.text = text;
    }

    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }
}
