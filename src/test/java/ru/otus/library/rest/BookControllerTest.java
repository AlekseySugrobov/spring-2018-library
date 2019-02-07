package ru.otus.library.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.library.Library;
import ru.otus.library.config.TestContext;
import ru.otus.library.dao.AuthorDAO;
import ru.otus.library.dao.GenreDAO;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.AuthorService;
import ru.otus.library.service.BookService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@DisplayName("Тесты BookController")
@ContextConfiguration(classes = {TestContext.class, Library.class})
@SpringBootTest
class BookControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private BookService bookService;
    @Autowired
    private GenreDAO genreDAO;
    @Autowired
    private AuthorDAO authorDAO;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Тест получения списка книг")
    void listBooks() throws Exception {
        when(bookService.findAll()).thenReturn(Collections
                .singletonList(new Book("book1", new Genre("genre1"), new Author("author1"))));
        this.mockMvc.perform(get("/books/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(view().name("books/list"));
        verify(bookService, times(1)).findAll();
    }

    @Test
    @DisplayName("Тест получения книги по ID")
    void getBook() throws Exception {
        Book book = new Book();
        book.setId("123");
        book.setName("123");
        Genre genre = new Genre();
        genre.setId("123");
        genre.setName("123");
        Author author = new Author();
        author.setId("123");
        author.setName("123");
        book.setGenre(genre);
        book.setAuthor(author);
        when(bookService.findById("123")).thenReturn(book);
        this.mockMvc.perform(get("/books/edit?id=123"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("books/edit"));
        verify(bookService, times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Тест создания книги")
    void createBook() throws Exception {
        this.mockMvc.perform(get("/books/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(view().name("books/edit"));
        verifyZeroInteractions(bookService);
    }

    @Test
    @DisplayName("Тест редактирования книги")
    void editBook() throws Exception {
        this.mockMvc.perform(post("/books/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("books/edit"));
    }
}