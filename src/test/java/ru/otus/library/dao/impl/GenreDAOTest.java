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
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"})
@DisplayName("Тесты GenreDAO")
@DataMongoTest
public class GenreDAOTest {

    @Autowired
    private GenreDAO genreDAO;
    private Genre genre;

    @BeforeEach
    public void setUp() {
        genreDAO.deleteAll();
        genre = new Genre("AUTHOR2");
        genreDAO.save(genre);
    }

    @Test
    @DisplayName("Тест создания жанра")
    public void edit() {
        assertThat(genre.getId()).isNotNull();
    }

    @Test
    @DisplayName("Тест полчения жанра по ID")
    public void getById() {
        Optional<Genre> currentGenre = genreDAO.findById(genre.getId());
        assertThat(currentGenre.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Тест получения всех жанров")
    public void getAll() {
        Iterable<Genre> allGenres = genreDAO.findAll();
        assertThat(allGenres).hasSize(1);
    }

    @Test
    @DisplayName("Тест удаления жанра")
    public void delete() {
        genreDAO.deleteById(genre.getId());
        Optional<Genre> currentGenre = genreDAO.findById(genre.getId());
        assertThat(currentGenre.isPresent()).isFalse();
    }
}