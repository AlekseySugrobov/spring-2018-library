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
        Genre genre = handleGenre(book.getGenre().getId(), "Введен не существующий жанр.");
        Author author = handleAuthor(book.getAuthor().getId(), "Введен не существующий автор");
        book.setGenre(genre);
        book.setAuthor(author);
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
    public void getBook(String id) throws UserInputProcessException {
        Optional<Book> optionalBook = bookDAO.findById(id);
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

    private Author handleAuthor(String authorId, String s) throws UserInputProcessException {
        Optional<Author> optionalAuthor = authorDAO.findById(authorId);
        return optionalAuthor.orElseThrow(() -> new UserInputProcessException(s));
    }

    private Genre handleGenre(String genreId, String s) throws UserInputProcessException {
        Optional<Genre> optionalGenre = genreDAO.findById(genreId);
        return optionalGenre.orElseThrow(() -> new UserInputProcessException(s));
    }

    @Override
    public void getAuthor(String id) throws UserInputProcessException {
        Optional<Author> optionalAuthor = authorDAO.findById(id);
        Author author = optionalAuthor.orElseThrow(() -> new UserInputProcessException("Невозможно найти автора по указанному идентификатору"));
        System.out.println("Автор: " + author.getName());
    }

    @Override
    public void getGenre(String id) throws UserInputProcessException {
        Genre genre = handleGenre(id, "Невозможно найти жанр по указанному идентификатору");
        System.out.println("Жанр: " + genre.getName());
    }

    @Override
    public void getAllBooks() {
        Iterable<Book> allBooks = bookDAO.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book : allBooks) {
            Optional<Genre> optionalGenre = genreDAO.findById(book.getGenre().getId());
            Optional<Author> optionalAuthor = authorDAO.findById(book.getAuthor().getId());
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
        Iterable<Genre> allGenres = genreDAO.findAll();
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
        Iterable<Author> allAuthors = authorDAO.findAll();
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
    public void addComment(String bookId, Comment comment) throws UserInputProcessException {
        Optional<Book> optionalBook = bookDAO.findById(bookId);
        comment.setBook(optionalBook.orElseThrow(() -> new UserInputProcessException("Невозможно найти книгу по указанному идентификатору")));
        commentDAO.save(comment);
    }

    @Override
    public void getAllComments() {
        Iterable<Comment> allComments = commentDAO.findAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Comment comment : allComments) {
            stringBuilder.append("ID: ")
                    .append(comment.getId())
                    .append("; Текст: ")
                    .append(comment.getText())
                    .append(NEW_ROW);
        }
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void getCommentsByBookId(String bookId) {
        List<Comment> comments = commentDAO.findByBookId(bookId);
        StringBuilder stringBuilder = new StringBuilder();
        for (Comment comment : comments) {
            stringBuilder.append("ID: ")
                    .append(comment.getId())
                    .append("; Текст: ")
                    .append(comment.getText())
                    .append(NEW_ROW);
        }
        System.out.println(stringBuilder.toString());
    }
}
