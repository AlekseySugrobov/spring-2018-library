package ru.otus.library.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.library.domain.Comment;

import java.util.List;

@Repository
public interface CommentDAO extends CrudRepository<Comment, Long> {
    List<Comment> findByBookId(long bookId);
}
