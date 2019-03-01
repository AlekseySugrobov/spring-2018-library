package ru.otus.library.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;

@Repository
public interface AuthorDAO extends ReactiveMongoRepository<Author, String> {
}
