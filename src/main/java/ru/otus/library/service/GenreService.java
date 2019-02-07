package ru.otus.library.service;

import ru.otus.library.domain.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();

    Genre findById(String id);

    void save(Genre genre);
}
