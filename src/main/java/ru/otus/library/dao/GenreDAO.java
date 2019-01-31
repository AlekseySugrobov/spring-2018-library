package ru.otus.library.dao;

import org.springframework.data.repository.CrudRepository;
import ru.otus.library.domain.Genre;

public interface GenreDAO extends CrudRepository<Genre, Long> {
}
