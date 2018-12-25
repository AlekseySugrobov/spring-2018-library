package ru.otus.library.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.domain.Author;
import ru.otus.library.mappers.AuthorMapper;

import java.util.*;

@Repository
public class AuthorDAOImpl implements AuthorDAO {

    private final NamedParameterJdbcOperations jdbc;
    private final RowMapper<Author> rowMapper;

    public AuthorDAOImpl(NamedParameterJdbcOperations jdbcOperations, RowMapper<Author> rowMapper) {
        this.jdbc = jdbcOperations;
        this.rowMapper = rowMapper;
    }

    @Override
    public void save(Author entity) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("id", entity.getId());
        params.put("name", entity.getName());
        if (!getById(entity.getId()).isPresent()) {
            jdbc.update("insert into AUTHORS(id, name) values (:id, :name)", params);
        } else {
            jdbc.update("update authors set name=:name where id=:id", params);
        }
    }

    @Override
    public Optional<Author> getById(long id) {
        final Map<String, Long> params = Collections.singletonMap("id", id);
        try {
            return Optional.ofNullable(jdbc.queryForObject("select * from authors where id=:id", params, rowMapper));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> getAll() {
        try {
            return jdbc.query("select * from authors", rowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public void delete(long id) {
        final Map<String, Long> params = Collections.singletonMap("id", id);
        jdbc.update("delete from authors where id=:id", params);
    }
}
