package ru.otus.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.domain.Genre;

public interface GenreDAO extends MongoRepository<Genre, String> {
}
