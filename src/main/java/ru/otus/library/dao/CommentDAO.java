package ru.otus.library.dao;

import ru.otus.library.domain.Comment;

import java.util.List;

public interface CommentDAO extends BasicDAO<Comment> {
    List<Comment> getCommentsByBookId(long bookId);
}
