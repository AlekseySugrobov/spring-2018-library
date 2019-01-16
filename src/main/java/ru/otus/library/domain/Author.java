package ru.otus.library.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author-generator")
    @SequenceGenerator(name = "author-generator", sequenceName = "author_seq", allocationSize = 1)
    private Long id;
    private String name;

    public Author(String name) {
        this.name = name;
    }
}
