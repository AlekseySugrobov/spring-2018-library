package ru.otus.library.service.impl;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.LibraryDataException;
import ru.otus.library.service.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDAO;

    public AuthorServiceImpl(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @Override
    public List<Author> findAll() {
        return authorDAO.findAll();
    }

    @Override
    public Author findById(String id) {
        return authorDAO.findById(id).orElseThrow(() -> new LibraryDataException("Can't find author by id" + id));
    }

    @Override
    public void save(Author author) {
        if (StringUtils.isEmptyOrWhitespace(author.getId())) {
            author.setId(null);
        }
        authorDAO.save(author);
    }

    @Override
    public void delete(String id) {
        if (StringUtils.isEmptyOrWhitespace(id)) {
            throw new LibraryDataException("Empty id");
        }
        authorDAO.deleteById(id);
    }
}
