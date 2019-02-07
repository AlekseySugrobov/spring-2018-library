package ru.otus.library.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.domain.Author;
import ru.otus.library.service.AuthorService;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/list")
    public String listAuthors(Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "authors/list";
    }

    @GetMapping("/edit")
    public String getAuthor(@RequestParam("id") String id, Model model) {
        Author author = authorService.findById(id);
        model.addAttribute("author", author);
        return "authors/edit";
    }

    @GetMapping("/create")
    public String createAuthor(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "authors/edit";
    }

    @PostMapping("/edit")
    public String editAuthor(@ModelAttribute Author author) {
        authorService.save(author);
        return "redirect:/authors/list";
    }
}
