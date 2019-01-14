package ru.otus.library.dao;

import java.util.List;
import java.util.Optional;

public interface BasicDAO<T> {
    void save(T entity);
    Optional<T> getById(long id);
    List<T> getAll();
    void delete(long id);
}
