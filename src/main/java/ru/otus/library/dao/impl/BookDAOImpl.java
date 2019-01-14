package ru.otus.library.dao.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.domain.Book;
import ru.otus.library.mappers.BookMapper;

import java.util.*;

@Repository
public class BookDAOImpl implements BookDAO {

    private final NamedParameterJdbcOperations jdbc;
    private final RowMapper<Book> rowMapper;

    public BookDAOImpl(NamedParameterJdbcOperations jdbcOperations, RowMapper<Book> rowMapper) {
        this.jdbc = jdbcOperations;
        this.rowMapper = rowMapper;
    }

    @Override
    public void save(Book entity) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", entity.getId());
        params.put("name", entity.getName());
        params.put("genreId", entity.getGenreId());
        params.put("authorId", entity.getAuthorId());
        if (!getById(entity.getId()).isPresent()) {
            jdbc.update("insert into books(id, name, genre_id, author_id) values (:id, :name, :genreId, :authorId)", params);
        } else {
            jdbc.update("update books set name=:name, genre_id=:genreId, author_id=:authorId where id=:id", params);
        }
    }

    @Override
    public Optional<Book> getById(long id) {
        final Map<String, Long> params = Collections.singletonMap("id", id);
        try {
            return Optional.ofNullable(jdbc.queryForObject("select id as id, name as name, genre_id as genreId, author_id as authorId from books where id=:id", params, rowMapper));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        try {
            return jdbc.query("select id as id, name as name, genre_id as genreId, author_id as authorId from books", rowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public void delete(long id) {
        final Map<String, Long> params = Collections.singletonMap("id", id);
        jdbc.update("delete from books where id=:id", params);
    }
}
