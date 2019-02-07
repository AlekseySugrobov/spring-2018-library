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

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @DisplayName("Тест получения списка жанров")
    void listGenres() throws Exception {
        when(genreService.findAll()).thenReturn(Collections.singletonList(new Genre("genre1")));
        this.mockMvc.perform(get("/genres/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("genres"))
                .andExpect(view().name("genres/list"));
        verify(genreService, times(1)).findAll();
    }

    @Test
    @DisplayName("Тест получения жанра по ID")
    void getGenre() throws Exception {
        Genre genre = new Genre();
        genre.setId("123");
        genre.setName("123");
        when(genreService.findById("123")).thenReturn(genre);
        this.mockMvc.perform(get("/genres/edit?id=123"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("genre"))
                .andExpect(view().name("genres/edit"));
        verify(genreService, times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Тест создания жанра")
    void createGenre() throws Exception {
        this.mockMvc.perform(get("/genres/create"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("genre"))
                .andExpect(view().name("genres/edit"));
    }

    @Test
    @DisplayName("Тест редактирования жанра")
    void editGenre() throws Exception {
        this.mockMvc.perform(post("/genres/edit"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/genres/list"));
        verify(genreService, times(1)).save(Mockito.any());
    }
}