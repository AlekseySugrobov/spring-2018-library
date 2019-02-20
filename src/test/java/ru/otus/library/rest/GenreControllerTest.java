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
import ru.otus.library.domain.Genre;
import ru.otus.library.service.GenreService;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Тесты GenreControllerTest")
@ContextConfiguration(classes = {TestContext.class, Library.class})
@SpringBootTest
class GenreControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private GenreService genreService;
    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Тест получения списка жанров")
    void listGenres() throws Exception {
        this.mockMvc.perform(get("/genres/"))
                .andExpect(status().isOk());
        verify(genreService, times(1)).findAll();
    }

    @Test
    @DisplayName("Тест получения жанра по ID")
    void getGenre() throws Exception {
        this.mockMvc.perform(get("/genres/123"))
                .andExpect(status().isOk());
        verify(genreService, times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Тест редактирования жанра")
    void editGenre() throws Exception {
        Genre genre = new Genre("new genre");
        this.mockMvc.perform(post("/genres/edit").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(genre)))
                .andExpect(status().isOk());
        verify(genreService, times(1)).save(Mockito.any());
    }
}