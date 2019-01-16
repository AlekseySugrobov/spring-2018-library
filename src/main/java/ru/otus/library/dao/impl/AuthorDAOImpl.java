package ru.otus.library.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.LibraryDataException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class AuthorDAOImpl implements AuthorDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Author entity) {
        if (Objects.isNull(entity.getId())) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    public Optional<Author> getById(long id) {
        Author author = entityManager.find(Author.class, id);
        if (Objects.isNull(author)) {
            return Optional.empty();
        }
        return Optional.of(author);
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = entityManager.createQuery("SELECT a FROM Author a", Author.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(long id) {
        Optional<Author> author = getById(id);
        if (author.isPresent()) {
            entityManager.remove(author.get());
        } else {
            throw new LibraryDataException("Can't find author by id" + id);
        }
    }
}
