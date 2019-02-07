package ru.otus.library.service;

import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.UserInputProcessException;

public interface InputService {
    void editBook(Book book) throws UserInputProcessException;
    void editAuthor(Author author);
    void editGenre(Genre genre);
    void getBook(String id) throws UserInputProcessException;
    void getAuthor(String id) throws UserInputProcessException;
    void getGenre(String id) throws UserInputProcessException;
    void getAllBooks();
    void getAllGenres();
    void getAllAuthors();
    void addComment(String bookId, Comment comment) throws UserInputProcessException;
    void getAllComments();
    void getCommentsByBookId(String bookId);
}
