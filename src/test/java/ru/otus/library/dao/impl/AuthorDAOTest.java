package ru.otus.library.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.domain.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DisplayName("Тесты AuthorDAO")
@DataMongoTest
public class AuthorDAOTest {

    @Autowired
    private AuthorDAO authorDAO;

    private Author author;


    @BeforeEach
    public void setUp() {
        authorDAO.deleteAll();
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
        Optional<Author> currentAuthor = authorDAO.findById(author.getId());
        assertThat(currentAuthor.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Тест получения всех авторов")
    public void getAll() {
        Iterable<Author> allAuthors = authorDAO.findAll();
        assertThat(allAuthors).hasSize(1);
    }

    @Test
    @DisplayName("Тест удаления автора")
    public void delete() {
        authorDAO.deleteById(author.getId());
        Optional<Author> currentAuthor = authorDAO.findById(author.getId());
        assertThat(currentAuthor.isPresent()).isFalse();
    }
}