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
import ru.otus.library.dao.CommentDAO;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Тесты CommentDAO")
public class CommentDAOImplTest {
    @TestConfiguration
    static class CommentDAOImplTestConfiguration{
        @Bean
        public CommentDAO commentDAO(){
            return new CommentDAOImpl();
        }
    }

    @Autowired
    private CommentDAO commentDAO;

    private Comment comment;
    private Book book;

    @BeforeEach
    public void setUp() {
        Author author = new Author("Author1");
        Genre genre = new Genre("Genre1");
        book = new Book("BOOK2", genre, author);
        comment = new Comment("Comment1", book);
        commentDAO.save(comment);
    }

    @Test
    @DisplayName("Сохранение комментария")
    public void edit() {
        assertThat(comment.getId()).isNotNull();
    }

    @Test
    @DisplayName("Тест получения комментария по ID")
    public void getById() {
        Optional<Comment> currentComment = commentDAO.getById(comment.getId());
        assertThat(currentComment).isPresent();
    }

    @Test
    @DisplayName("Тест получения всех комментариев")
    public void getAll() {
        List<Comment> allComments = commentDAO.getAll();
        assertThat(allComments).hasSize(1);
    }

    @Test
    @DisplayName("Тест получения комментариев по ID книги")
    public void getCommentsByBookId() {
        List<Comment> comments = commentDAO.getCommentsByBookId(book.getId());
        assertThat(comments).hasSize(1);
    }

    @Test
    @DisplayName("Тест удаления комментария")
    public void delete() {
        commentDAO.delete(comment.getId());
        Optional<Comment> currentComment = commentDAO.getById(comment.getId());
        assertThat(currentComment.isPresent()).isFalse();
    }
}
