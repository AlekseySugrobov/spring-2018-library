package ru.otus.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"})
@DisplayName("Тесты BookDAO")
public class BookDAOImplTest {

    @Autowired
    private BookDAO dao;

    @Test
    @DisplayName("Тест создания книги")
    public void edit() {
        Book book = new Book(2L, "BOOK2", 1L, 1L);
        dao.edit(book);
        Optional<Book> currentBook = dao.getById(2);
        assertThat(currentBook.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Тест получение книги по ID")
    public void getById() {
        Optional<Book> currentBook = dao.getById(1);
        assertThat(currentBook.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Тест получения всех книг")
    public void getAll() {
        List<Book> allBooks = dao.getAll();
        assertThat(allBooks).isNotNull();
    }

    @Test
    @DisplayName("Тест удаления книги")
    public void delete() {
        Book book = new Book(2L, "BOOK2", 1L, 1L);
        dao.edit(book);
        dao.delete(2);
        Optional<Book> currentBook = dao.getById(2);
        assertThat(currentBook.isPresent()).isFalse();
    }
}