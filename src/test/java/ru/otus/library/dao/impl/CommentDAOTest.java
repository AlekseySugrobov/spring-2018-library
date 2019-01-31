package ru.otus.library.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
@DisplayName("Тесты CommentDAO")
@DataMongoTest
public class CommentDAOTest {

    @Autowired
    private CommentDAO commentDAO;

    private Comment comment;
    private Book book;

    @BeforeEach
    public void setUp() {
        commentDAO.deleteAll();
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
        Optional<Comment> currentComment = commentDAO.findById(comment.getId());
        assertThat(currentComment).isPresent();
    }

    @Test
    @DisplayName("Тест получения всех комментариев")
    public void getAll() {
        Iterable<Comment> allComments = commentDAO.findAll();
        assertThat(allComments).hasSize(1);
    }

    @Test
    @DisplayName("Тест получения комментариев по ID книги")
    public void getCommentsByBookId() {
        List<Comment> comments = commentDAO.findByBookId(book.getId());
        assertThat(comments).hasSize(1);
    }

    @Test
    @DisplayName("Тест удаления комментария")
    public void delete() {
        commentDAO.deleteById(comment.getId());
        Optional<Comment> currentComment = commentDAO.findById(comment.getId());
        assertThat(currentComment.isPresent()).isFalse();
    }
}
