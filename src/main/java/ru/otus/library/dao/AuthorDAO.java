package ru.otus.library.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Author;

@Repository
public interface AuthorDAO extends CrudRepository<Author, Long> {
}
