package ru.otus.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.domain.Author;

public interface AuthorDAO extends MongoRepository<Author, String> {
}
