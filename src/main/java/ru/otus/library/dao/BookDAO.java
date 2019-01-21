package ru.otus.library.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Book;

@Repository
public interface BookDAO extends CrudRepository<Book, Long> {
}
