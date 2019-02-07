package ru.otus.library.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Genre;
import ru.otus.library.service.GenreService;

import java.util.List;

@Controller
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/list")
    public String listGenres(Model model) {
        List<Genre> genres = genreService.findAll();
        model.addAttribute("genres", genres);
        return "genres/list";
    }

    @GetMapping("/edit")
    public String getGenre(@RequestParam("id") String id, Model model) {
        Genre genre = genreService.findById(id);
        model.addAttribute("genre", genre);
        return "genres/edit";
    }

    @GetMapping("/create")
    public String createGenre(Model model) {
        Genre genre = new Genre();
        model.addAttribute("genre", genre);
        return "genres/edit";
    }

    @PostMapping("/edit")
    public String editGenre(@ModelAttribute Genre genre) {
        genreService.save(genre);
        return "redirect:/genres/list";
    }
}
