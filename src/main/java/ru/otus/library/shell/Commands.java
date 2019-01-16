package ru.otus.library.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;
import ru.otus.library.exception.UserInputProcessException;
import ru.otus.library.service.InputService;

@ShellComponent
public class Commands {
    private final InputService inputService;

    public Commands(InputService inputService) {
        this.inputService = inputService;
    }

    @ShellMethod("Getting book by id")
    public void getBook(@ShellOption long id) {
        try {
            this.inputService.getBook(id);
        } catch (UserInputProcessException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @ShellMethod("Getting author by id")
    public void getAuthor(@ShellOption long id) {
        try {
            this.inputService.getAuthor(id);
        } catch (UserInputProcessException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @ShellMethod("Getting genre by id")
    public void getGenre(@ShellOption long id) {
        try {
            this.inputService.getGenre(id);
        } catch (UserInputProcessException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @ShellMethod("Getting all books")
    public void getAllBooks() {
        this.inputService.getAllBooks();
    }

    @ShellMethod("Getting all authors")
    public void getAllAuthors() {
        this.inputService.getAllAuthors();
    }

    @ShellMethod("Getting all genres")
    public void getAllGenres() {
        this.inputService.getAllGenres();
    }

    @ShellMethod("Creating book")
    public void createBook(@ShellOption String name,
                           @ShellOption Genre genre,
                           @ShellOption Author author){
        Book book = new Book(name, genre, author);
        try {
            this.inputService.editBook(book);
        } catch (UserInputProcessException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @ShellMethod("Editing book")
    public void editBook(@ShellOption long id,
                         @ShellOption String name,
                         @ShellOption Genre genre,
                         @ShellOption Author author) {
        Book book = new Book(id, name, author, genre);
        try {
            this.inputService.editBook(book);
        } catch (UserInputProcessException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @ShellMethod("Create author")
    public void createAuthor(@ShellOption String name) {
        Author author = new Author(name);
        this.inputService.editAuthor(author);
    }

    @ShellMethod("Editing author")
    public void editAuthor(@ShellOption long id,
                           @ShellOption String name) {
        Author author = new Author(id, name);
        this.inputService.editAuthor(author);
    }

    @ShellMethod("Editing genre")
    public void editGenre(@ShellOption long id,
                          @ShellOption String name) {
        Genre genre = new Genre(id, name);
        this.inputService.editGenre(genre);
    }

    @ShellMethod("Create genre")
    public void createGenre(@ShellOption String name) {
        Genre genre = new Genre(name);
        this.inputService.editGenre(genre);
    }

    @ShellMethod("Adding comment")
    public void addComment(@ShellOption long bookId,
                           @ShellOption String text) {
        Comment comment = new Comment(text);
        try {
            this.inputService.addComment(bookId, comment);
        } catch (UserInputProcessException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @ShellMethod("Getting all comments")
    public void getAllComments() {
        this.inputService.getAllComments();
    }

    @ShellMethod("Getting comments by book id")
    public void getComments(@ShellOption long bookId) {
        this.inputService.getCommentsByBookId(bookId);
    }
}
