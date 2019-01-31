package ru.otus.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.domain.Book;

public interface BookDAO extends MongoRepository<Book, String> {
}
