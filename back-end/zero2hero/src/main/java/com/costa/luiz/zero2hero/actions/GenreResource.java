package com.costa.luiz.zero2hero.actions;

import com.costa.luiz.zero2hero.model.genre.Genre;
import com.costa.luiz.zero2hero.repository.GenreRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/genres")
@Tag(name="Genres")
public class GenreResource {

    private final GenreRepository repository;

    @GetMapping
    public List<Genre> all() {
        log.info("Getting all genres");
        return repository.findAll();
    }

}
