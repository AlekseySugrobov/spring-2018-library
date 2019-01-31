package ru.otus.library.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.library.dao.BookDAOExtended;
import ru.otus.library.dao.CommentDAO;
import ru.otus.library.domain.Book;

public class BookDAOExtendedImpl implements BookDAOExtended {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private CommentDAO commentDAO;

    @Override
    public void fullDelete(Book book) {
        commentDAO.deleteAllByBookId(book.getId());
        mongoOperations.remove(book);
    }
}
