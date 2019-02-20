package ru.otus.library.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.LibraryDataException;
import ru.otus.library.service.GenreService;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDAO genreDAO;

    public GenreServiceImpl(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    @Override
    public List<Genre> findAll() {
        return genreDAO.findAll();
    }

    @Override
    public Genre findById(String id) {
        return genreDAO.findById(id).orElseThrow(() -> new LibraryDataException("Can't find genre by id" + id));
    }

    @Override
    public void save(Genre genre) {
        if (StringUtils.isBlank(genre.getId())) {
            genre.setId(null);
        }
        genreDAO.save(genre);
    }
}
