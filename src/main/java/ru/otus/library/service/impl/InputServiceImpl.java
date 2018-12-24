package ru.otus.library.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.UserInputProcessException;
import ru.otus.library.service.InputService;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class InputServiceImpl implements InputService {

    private static final String NEW_ROW = "\r\n";
    private final BookDAO bookDAO;
    private final GenreDAO genreDAO;
    private final AuthorDAO authorDAO;

    public InputServiceImpl(BookDAO bookDAO, GenreDAO genreDAO, AuthorDAO authorDAO) {
        this.bookDAO = bookDAO;
        this.genreDAO = genreDAO;
        this.authorDAO = authorDAO;
    }

    @Override
    public void editBook(Book book) throws UserInputProcessException {
        handleGenre(book.getGenreId(), "Введен не существующий жанр.");
        handleAuthor(book.getAuthorId(), "Введен не существующий автор");
        bookDAO.edit(book);
    }

    @Override
    public void editAuthor(Author author) {
        authorDAO.edit(author);
    }

    @Override
    public void editGenre(Genre genre) {
        genreDAO.edit(genre);
    }

    @Override
    public void getBook(long id) throws UserInputProcessException {
        Optional<Book> optionalBook = bookDAO.getById(id);
        if (!optionalBook.isPresent()) {
            throw new UserInputProcessException("Невозможно найти книгу по указанному идентификатору");
        }
        Book book = optionalBook.get();
        Genre genre = handleGenre(book.getGenreId(), "Невозможно найти жанр по идентификатору " + book.getGenreId());
        Author author = handleAuthor(book.getAuthorId(), "Невозможно найти автора по идентификатору " + book.getAuthorId());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Книга: ")
                .append(book.getName())
                .append(NEW_ROW)
                .append("Автор: ")
                .append(author.getName())
                .append(NEW_ROW)
                .append("Жанр: ")
                .append(genre.getName());
        System.out.println(stringBuilder.toString());
    }

    private Author handleAuthor(Long authorId, String s) throws UserInputProcessException {
        Optional<Author> optionalAuthor = authorDAO.getById(authorId);
        if (!optionalAuthor.isPresent()) {
            throw new UserInputProcessException(s);
        }
        return optionalAuthor.get();
    }

    private Genre handleGenre(Long genreId, String s) throws UserInputProcessException {
        Optional<Genre> optionalGenre = genreDAO.getById(genreId);
        if (!optionalGenre.isPresent()) {
            throw new UserInputProcessException(s);
        }
        return optionalGenre.get();
    }

    @Override
    public void getAuthor(long id) throws UserInputProcessException {
        Optional<Author> optionalAuthor = authorDAO.getById(id);
        if (!optionalAuthor.isPresent()) {
            throw new UserInputProcessException("Невозможно найти автора по указанному идентификатору");
        }
        Author author = optionalAuthor.get();
        System.out.println("Автор: " + author.getName());
    }

    @Override
    public void getGenre(long id) throws UserInputProcessException {
        Genre genre = handleGenre(id, "Невозможно найти жанр по указанному идентификатору");
        System.out.println("Жанр: " + genre.getName());
    }

    @Override
    public void getAllBooks() {
        List<Book> allBooks = bookDAO.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book : allBooks) {
            Optional<Genre> optionalGenre = genreDAO.getById(book.getGenreId());
            Optional<Author> optionalAuthor = authorDAO.getById(book.getAuthorId());
            stringBuilder.append("Книга: ")
                    .append(book.getName())
                    .append(NEW_ROW)
                    .append("Автор: ")
                    .append(optionalAuthor.isPresent() ? optionalAuthor.get().getName() : "Не удалось найти")
                    .append(NEW_ROW)
                    .append("Жанр: ")
                    .append(optionalGenre.isPresent() ? optionalGenre.get().getName() : "Не удалось найти")
                    .append(NEW_ROW);
        }
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void getAllGenres() {
        List<Genre> allGenres = genreDAO.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Genre genre : allGenres) {
            stringBuilder.append("ID: ")
                    .append(genre.getId())
                    .append("; Наменование: ")
                    .append(genre.getName())
                    .append(NEW_ROW);
        }
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void getAllAuthors() {
        List<Author> allAuthors = authorDAO.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Author author : allAuthors) {
            stringBuilder.append("ID: ")
                    .append(author.getId())
                    .append("; Наменование: ")
                    .append(author.getName())
                    .append(NEW_ROW);
        }
        System.out.println(stringBuilder.toString());
    }
}
