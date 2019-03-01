package ru.otus.library.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.domain.Author;
import ru.otus.library.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDAO;

    public AuthorServiceImpl(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @Override
    public Flux<Author> findAll() {
        return authorDAO.findAll();
    }

    @Override
    public Mono<Author> findById(String id) {
        return authorDAO.findById(id);
    }

    @Override
    public Mono<Author> save(Author author) {
        if (StringUtils.isEmpty(author.getId())) {
            author.setId(null);
        }
        return authorDAO.save(author);
    }

    @Override
    public Mono<Void> delete(String id) {
        return authorDAO.findById(id)
                .flatMap(this.authorDAO::delete);
    }
}
