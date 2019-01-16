package ru.otus.library.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre-generator")
    @SequenceGenerator(name = "genre-generator", sequenceName = "genre_seq", allocationSize = 1)
    private Long id;
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
