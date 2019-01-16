package ru.otus.library.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"})
@DataJpaTest
@DisplayName("Тесты GenreDAO")
public class GenreDAOImplTest {

    @TestConfiguration
    static class GenreDaoImplTestConfiguration {
        @Bean
        public GenreDAO genreDAO(){
            return new GenreDAOImpl();
        }
    }

    @Autowired
    private GenreDAO genreDAO;
    private Genre genre;

    @BeforeEach
    public void setUp() {
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
        Optional<Genre> currentGenre = genreDAO.getById(genre.getId());
        assertThat(currentGenre.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Тест получения всех жанров")
    public void getAll() {
        List<Genre> allGenres = genreDAO.getAll();
        assertThat(allGenres).hasSize(1);
    }

    @Test
    @DisplayName("Тест удаления жанра")
    public void delete() {
        genreDAO.delete(genre.getId());
        Optional<Genre> currentGenre = genreDAO.getById(genre.getId());
        assertThat(currentGenre.isPresent()).isFalse();
    }
}