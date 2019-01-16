package ru.otus.library.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.dao.CommentDAO;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.UserInputProcessException;
import ru.otus.library.service.InputService;

import java.util.List;
import java.util.Optional;

@Service
public class InputServiceImpl implements InputService {

    private static final String NEW_ROW = "\r\n";
    private final BookDAO bookDAO;
    private final GenreDAO genreDAO;
    private final AuthorDAO authorDAO;
    private final CommentDAO commentDAO;

    public InputServiceImpl(BookDAO bookDAO, GenreDAO genreDAO, AuthorDAO authorDAO, CommentDAO commentDAO) {
        this.bookDAO = bookDAO;
        this.genreDAO = genreDAO;
        this.authorDAO = authorDAO;
        this.commentDAO = commentDAO;
    }

    @Override
    public void editBook(Book book) throws UserInputProcessException {
        handleGenre(book.getGenre().getId(), "Введен не существующий жанр.");
        handleAuthor(book.getAuthor().getId(), "Введен не существующий автор");
        bookDAO.save(book);
    }

    @Override
    public void editAuthor(Author author) {
        authorDAO.save(author);
        System.out.println("author = [" + author + "]");
    }

    @Override
    public void editGenre(Genre genre) {
        genreDAO.save(genre);
    }

    @Override
    public void getBook(long id) throws UserInputProcessException {
        Optional<Book> optionalBook = bookDAO.getById(id);
        if (!optionalBook.isPresent()) {
            throw new UserInputProcessException("Невозможно найти книгу по указанному идентификатору");
        }
        Book book = optionalBook.get();
        Genre genre = handleGenre(book.getGenre().getId(), "Невозможно найти жанр по идентификатору " + book.getGenre().getId());
        Author author = handleAuthor(book.getAuthor().getId(), "Невозможно найти автора по идентификатору " + book.getAuthor().getId());
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
            Optional<Genre> optionalGenre = genreDAO.getById(book.getGenre().getId());
            Optional<Author> optionalAuthor = authorDAO.getById(book.getAuthor().getId());
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

    @Override
    @Transactional
    public void addComment(Long bookId, Comment comment) throws UserInputProcessException {
        Optional<Book> optionalBook = bookDAO.getById(bookId);
        if (!optionalBook.isPresent()) {
            throw new UserInputProcessException("Невозможно найти книгу по указанному идентификатору");
        }
        comment.setBook(optionalBook.get());
        commentDAO.save(comment);
    }

    @Override
    public void getAllComments() {
        List<Comment> allComments = commentDAO.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        for(Comment comment:allComments) {
            stringBuilder.append("ID: ")
                    .append(comment.getId())
                    .append("; Текст: ")
                    .append(comment.getText())
                    .append(NEW_ROW);
        }
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void getCommentsByBookId(long bookId) {
        List<Comment> comments = commentDAO.getCommentsByBookId(bookId);
        StringBuilder stringBuilder = new StringBuilder();
        for(Comment comment:comments) {
            stringBuilder.append("ID: ")
                    .append(comment.getId())
                    .append("; Текст: ")
                    .append(comment.getText())
                    .append(NEW_ROW);
        }
        System.out.println(stringBuilder.toString());
    }
}
