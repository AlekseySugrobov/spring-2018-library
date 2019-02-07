package ru.otus.library.service;

import ru.otus.library.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();

    Author findById(String id);

    void save(Author author);

    void delete(String id);
}
