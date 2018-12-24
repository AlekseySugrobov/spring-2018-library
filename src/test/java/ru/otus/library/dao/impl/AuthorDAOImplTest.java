package ru.otus.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"})
@DisplayName("Тесты AuthorDAO")
public class AuthorDAOImplTest {

    @Autowired
    private AuthorDAO dao;

    @Test
    @DisplayName("Тест создания автора")
    public void edit() {
        Author author = new Author(2L, "AUTHOR2");
        dao.edit(author);
        Optional<Author> currentAuthor = dao.getById(2);
        assertThat(currentAuthor.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Тест получения автора по ID")
    public void getById() {
        Optional<Author> currentAuthor = dao.getById(1);
        assertThat(currentAuthor.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Тест получения всех авторов")
    public void getAll() {
        List<Author> allAuthors = dao.getAll();
        assertThat(allAuthors).isNotNull();
    }

    @Test
    @DisplayName("Тест удаления автора")
    public void delete() {
        Author author = new Author(2L, "AUTHOR2");
        dao.edit(author);
        dao.delete(2);
        Optional<Author> currentAuthor = dao.getById(2);
        assertThat(currentAuthor.isPresent()).isFalse();
    }
}