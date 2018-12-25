package ru.otus.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"})
@DisplayName("Тесты GenreDAO")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:testschema.sql", "classpath:testdata.sql"})
})
public class GenreDAOImplTest {

    @Autowired
    private GenreDAO dao;

    @Test
    @DisplayName("Тест создания жанра")
    public void edit() {
        Genre genre = new Genre(2L, "GENRE2");
        dao.save(genre);
        Optional<Genre> currentGenre = dao.getById(2);
        assertThat(currentGenre.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Тест полчения жанра по ID")
    public void getById() {
        Optional<Genre> currentGenre = dao.getById(1);
        assertThat(currentGenre.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Тест получения всех жанров")
    public void getAll() {
        List<Genre> allGenres = dao.getAll();
        assertThat(allGenres).hasSize(1);
    }

    @Test
    @DisplayName("Тест удаления жанра")
    public void delete() {
        Genre genre = new Genre(2L, "GENRE2");
        dao.save(genre);
        dao.delete(2);
        Optional<Genre> currentGenre = dao.getById(2);
        assertThat(currentGenre.isPresent()).isFalse();
    }
}