package ru.otus.library.dao.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"})
@DisplayName("Тесты BookDAO")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:testschema.sql", "classpath:testdata.sql"})
})
public class BookDAOImplTest {

    @Autowired
    private BookDAO dao;

    @BeforeEach
    public void setUp() {
        Author author = new Author(1L, "Author1");
        Genre genre = new Genre(1L, "Genre1");
        Book book = new Book(2L, "BOOK2", author, genre);
        dao.save(book);
    }

    @Test
    @DisplayName("Тест создания книги")
    public void edit() {
        Author author = new Author(1L, "Author1");
        Genre genre = new Genre(1L, "Genre1");
        Book book = new Book(2L, "BOOK2", author, genre);
        dao.save(book);
        Optional<Book> currentBook = dao.getById(2);
        assertThat(currentBook.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Тест получение книги по ID")
    public void getById() {
        Optional<Book> currentBook = dao.getById(2);
        assertThat(currentBook.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Тест получения всех книг")
    public void getAll() {
        List<Book> allBooks = dao.getAll();
        assertThat(allBooks).hasSize(1);
    }

    @Test
    @DisplayName("Тест удаления книги")
    public void delete() {
        Author author = new Author(1L, "Author1");
        Genre genre = new Genre(1L, "Genre1");
        Book book = new Book(2L, "BOOK2", author, genre);
        dao.save(book);
        dao.delete(2);
        Optional<Book> currentBook = dao.getById(2);
        assertThat(currentBook.isPresent()).isFalse();
    }
}