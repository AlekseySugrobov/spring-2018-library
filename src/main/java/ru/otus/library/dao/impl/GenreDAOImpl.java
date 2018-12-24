package ru.otus.library.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Genre;
import ru.otus.library.mappers.GenreMapper;

import java.util.*;

@Repository
public class GenreDAOImpl implements GenreDAO {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDAOImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbc = jdbcOperations;
    }

    @Override
    public void edit(Genre entity) {
        final Map<String, Object> params = new HashMap<>(2);
        params.put("id", entity.getId());
        params.put("name", entity.getName());
        if (!getById(entity.getId()).isPresent()) {
            jdbc.update("insert into GENRES(id, name) values (:id, :name)", params);
        } else {
            jdbc.update("update genres set name=:name where id=:id", params);
        }
    }

    @Override
    public Optional<Genre> getById(long id) {
        final Map<String, Long> params = Collections.singletonMap("id", id);
        try {
            return Optional.ofNullable(jdbc.queryForObject("select * from genres where id=:id", params, GenreMapper.INSTANCE));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> getAll() {
        try {
            return jdbc.query("select * from genres", GenreMapper.INSTANCE);
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public void delete(long id) {
        final Map<String, Long> params = Collections.singletonMap("id", id);
        jdbc.update("delete from genres where id=:id", params);
    }
}
