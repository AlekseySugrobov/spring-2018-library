package ru.otus.library.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Тесты AuthorDAO")
public class AuthorDAOImplTest {

    @TestConfiguration
    static class AuthorDaoImplTestConfiguration {
        @Bean
        public AuthorDAO authorDAO() {
            return new AuthorDAOImpl();
        }
    }

    @Autowired
    private AuthorDAO authorDAO;

    private Author author;

    @BeforeEach
    public void setUp() {
        author = new Author("AUTHOR2");
        authorDAO.save(author);
    }

    @Test
    @DisplayName("Тест создания автора")
    public void edit() {
        assertThat(author.getId()).isNotNull();
    }

    @Test
    @DisplayName("Тест получения автора по ID")
    public void getById() {
        Optional<Author> currentAuthor = authorDAO.getById(author.getId());
        assertThat(currentAuthor.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Тест получения всех авторов")
    public void getAll() {
        List<Author> allAuthors = authorDAO.getAll();
        assertThat(allAuthors).hasSize(1);
    }

    @Test
    @DisplayName("Тест удаления автора")
    public void delete() {
        authorDAO.delete(author.getId());
        Optional<Author> currentAuthor = authorDAO.getById(author.getId());
        assertThat(currentAuthor.isPresent()).isFalse();
    }
}