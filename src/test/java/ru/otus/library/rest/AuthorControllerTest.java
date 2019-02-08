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
import ru.otus.library.domain.Author;
import ru.otus.library.service.AuthorService;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Тесты AuthorController")
@ContextConfiguration(classes = {TestContext.class, Library.class})
@SpringBootTest
class AuthorControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private AuthorService authorService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Тест получения списка авторов")
    void listAuthors() throws Exception {
        when(authorService.findAll()).thenReturn(Collections.singletonList(new Author("author1")));
        this.mockMvc.perform(get("/authors/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("authors"))
                .andExpect(view().name("authors/list"));
        verify(authorService, times(1)).findAll();
    }

    @Test
    @DisplayName("Тест получения автора по ID")
    void getAuthor() throws Exception {
        Author author = new Author();
        author.setId("123");
        author.setName("123");
        when(authorService.findById("123")).thenReturn(author);
        this.mockMvc.perform(get("/authors/edit?id=123"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("authors/edit"));
        verify(authorService, times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Тест создания автора")
    void createAuthor() throws Exception {
        this.mockMvc.perform(get("/authors/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("author"))
                .andExpect(view().name("authors/edit"));
        verifyZeroInteractions(authorService);
    }

    @Test
    @DisplayName("Тест редактирования автора")
    void editAuthor() throws Exception {
        this.mockMvc.perform(post("/authors/edit"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/authors/list"));
        verify(authorService, times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Тест удаления автора")
    void deleteAuthor() throws Exception {
        this.mockMvc.perform(get("/authors/delete?id=123"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/authors/list"));
        verify(authorService, times(1)).delete(Mockito.any());
    }
}