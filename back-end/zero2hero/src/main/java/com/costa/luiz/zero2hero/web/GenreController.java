package com.costa.luiz.zero2hero.web;

import com.costa.luiz.zero2hero.model.movie.Genre;
import com.costa.luiz.zero2hero.repository.GenreRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genres")
@Tag(name="Genres")
public class GenreController {

    private final GenreRepository repository;

    @GetMapping
    public List<Genre> all() {
        return repository.findAll();
    }
}
