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
import ru.otus.library.domain.Author;
import ru.otus.library.service.AuthorService;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Тест получения списка авторов")
    void listAuthors() throws Exception {
        this.mockMvc.perform(get("/authors/"))
                .andExpect(status().isOk());
        verify(authorService, times(1)).findAll();
    }

    @Test
    @DisplayName("Тест получения автора по ID")
    void getAuthor() throws Exception {
        this.mockMvc.perform(get("/authors/123"))
                .andExpect(status().isOk());
        verify(authorService, times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Тест редактирования автора")
    void editAuthor() throws Exception {
        Author author = new Author("new-name");
        this.mockMvc.perform(post("/authors/edit").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk());
        verify(authorService, times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Тест удаления автора")
    void deleteAuthor() throws Exception {
        this.mockMvc.perform(delete("/authors/123"))
                .andExpect(status().isOk());
        verify(authorService, times(1)).delete(Mockito.any());
    }
}