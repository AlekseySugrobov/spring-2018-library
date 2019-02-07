package ru.otus.library.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.dao.CommentDAO;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"})
@DisplayName("Тесты BookDAO")
@DataMongoTest
public class BookDAOTest {

    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private AuthorDAO authorDAO;
    @Autowired
    private CommentDAO commentDAO;
    private Book book;
    private Author author;

    @BeforeEach
    public void setUp() {
        bookDAO.deleteAll();
        author = new Author("Author1");
        Genre genre = new Genre("Genre1");
        book = new Book("BOOK2", genre, author);
        bookDAO.save(book);
    }

    @Test
    @DisplayName("Тест создания книги")
    public void edit() {
        assertThat(book.getId()).isNotNull();
    }

    @Test
    @DisplayName("Тест получение книги по ID")
    public void getById() {
        Optional<Book> currentBook = bookDAO.findById(book.getId());
        assertThat(currentBook.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Тест получения всех книг")
    public void getAll() {
        Iterable<Book> allBooks = bookDAO.findAll();
        assertThat(allBooks).hasSize(1);
    }

    @Test
    @DisplayName("Тест удаления книги")
    public void delete() {
        bookDAO.deleteById(book.getId());
        Optional<Book> currentBook = bookDAO.findById(book.getId());
        assertThat(currentBook.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Тест изменения имени автора книги")
    public void editAuthor() {
        String newAuthorName = "NewAuthorName";
        author.setName(newAuthorName);
        authorDAO.save(author);
        assertThat(authorDAO.findById(author.getId()).get().getName()).isEqualTo(newAuthorName);
        Book book2 = bookDAO.findById(book.getId()).get();
        assertThat(book2.getAuthor().getName()).isEqualTo(newAuthorName);
        bookDAO.delete(book2);
        assertThat(bookDAO.findById(book2.getId()).isPresent()).isFalse();
    }

    @Test
    @DisplayName("Тест добавления и удаления комментария")
    public void addAndDeleteComment() {
        Comment comment = new Comment("Comment1", book);
        commentDAO.save(comment);
        bookDAO.fullDelete(book);
        assertThat(commentDAO.findByBookId(book.getId())).isEmpty();
        assertThat(bookDAO.findById(book.getId()).isPresent()).isFalse();
    }
}