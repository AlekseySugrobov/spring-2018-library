package ru.otus.library.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.LibraryDataException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class GenreDAOImpl implements GenreDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Genre entity) {
        if (Objects.isNull(entity.getId())) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    public Optional<Genre> getById(long id) {
        Genre genre = entityManager.find(Genre.class, id);
        if (Objects.isNull(genre)) {
            return Optional.empty();
        }
        return Optional.of(genre);
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = entityManager.createQuery("SELECT g FROM Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(long id) {
        Optional<Genre> genre = getById(id);
        if (genre.isPresent()) {
            entityManager.remove(genre.get());
        } else {
            throw new LibraryDataException("Can't find genre by id" + id);
        }
    }
}
