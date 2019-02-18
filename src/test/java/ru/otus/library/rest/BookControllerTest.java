package ru.otus.library.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
import ru.otus.library.service.BookService;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Тест получения списка книг")
    void listBooks() throws Exception {
        this.mockMvc.perform(get("/books/"))
                .andExpect(status().isOk());
        verify(bookService, times(1)).findAll();
    }

    @Test
    @DisplayName("Тест получения книги по ID")
    void getBook() throws Exception {
        this.mockMvc.perform(get("/books/123"))
                .andExpect(status().isOk());
        verify(bookService, times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Тест редактирования книги")
    void editBook() throws Exception {
        Book book = new Book("new book", new Genre(UUID.randomUUID().toString(), "new genre"),
                new Author(UUID.randomUUID().toString(), "new author"));
        this.mockMvc.perform(post("/books/edit").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }
}