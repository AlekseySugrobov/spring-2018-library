package ru.otus.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.domain.Comment;

import java.util.List;

public interface CommentDAO extends MongoRepository<Comment, String> {
    List<Comment> findByBookId(String bookId);
    void deleteAllByBookId(String bookId);
}
