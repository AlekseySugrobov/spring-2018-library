package ru.otus.library.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.domain.Book;
import ru.otus.library.exception.LibraryDataException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class BookDAOImpl implements BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Book entity) {
        if (Objects.isNull(entity.getId())) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
    }

    @Override
    public Optional<Book> getById(long id) {
        Book book = entityManager.find(Book.class, id);
        if (Objects.isNull(book)) {
            return Optional.empty();
        }
        return Optional.of(book);
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b", Book.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(long id) {
        Optional<Book> book = getById(id);
        if (book.isPresent()) {
            entityManager.remove(book.get());
        } else {
            throw new LibraryDataException("Can't find book by id " + id);
        }
    }
}
