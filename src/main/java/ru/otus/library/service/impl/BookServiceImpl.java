package ru.otus.library.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.dao.BookDAO;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Book;
import ru.otus.library.exception.LibraryDataException;
import ru.otus.library.service.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final GenreDAO genreDAO;

    public BookServiceImpl(BookDAO bookDAO, AuthorDAO authorDAO, GenreDAO genreDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.genreDAO = genreDAO;
    }

    @Override
    public List<Book> findAll() {
        return bookDAO.findAll();
    }

    @Override
    public Book findById(String id) {
        return bookDAO.findById(id).orElseThrow(() -> new LibraryDataException("Can't find book by id" + id));
    }

    @Override
    public void save(Book book) {
        if (StringUtils.isEmpty(book.getId())) {
            book.setId(null);
        }
        book.setAuthor(authorDAO.findById(book.getAuthor().getId())
                .orElseThrow(() -> new LibraryDataException("Can't find author by id " + book.getAuthor().getId())));
        book.setGenre(genreDAO.findById(book.getGenre().getId())
                .orElseThrow(() -> new LibraryDataException("Can't find genre by id " + book.getGenre().getId())));
        bookDAO.save(book);
    }
}
