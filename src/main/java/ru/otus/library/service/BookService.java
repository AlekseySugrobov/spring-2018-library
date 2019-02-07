package ru.otus.library.service;

import ru.otus.library.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findById(String id);

    void save(Book book);
}
