package ru.otus.library.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.CommentDAO;
import ru.otus.library.domain.Comment;
import ru.otus.library.exception.LibraryDataException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class CommentDAOImpl implements CommentDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Comment entity) {
        if (Objects.isNull(entity.getId())) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    public Optional<Comment> getById(long id) {
        Comment comment = entityManager.find(Comment.class, id);
        if (Objects.isNull(comment)) {
            return Optional.empty();
        }
        return Optional.of(comment);
    }

    @Override
    public List<Comment> getAll() {
        TypedQuery<Comment> query = entityManager.createQuery("SELECT c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    public List<Comment> getCommentsByBookId(long bookId) {
        TypedQuery<Comment> query = entityManager.createQuery("SELECT c FROM Comment c JOIN c.book b WHERE b.id=:id", Comment.class);
        query.setParameter("id", bookId);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(long id) {
        Optional<Comment> comment = getById(id);
        if (comment.isPresent()) {
            entityManager.remove(comment.get());
        } else {
            throw new LibraryDataException("Can't find comment by id" + id);
        }
    }
}
