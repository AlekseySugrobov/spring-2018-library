package ru.otus.library.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection="genres")
public class Genre {
    @Id
    @NotBlank(message = "Укажите идентификатор жарна")
    private String id;
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
