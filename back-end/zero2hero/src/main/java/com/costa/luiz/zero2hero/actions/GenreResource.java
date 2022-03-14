package com.costa.luiz.zero2hero.actions;

import com.costa.luiz.zero2hero.model.genre.Genre;
import com.costa.luiz.zero2hero.model.genre.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genres")
public class GenreResource {

    @Autowired
    private final GenreRepository repository;


    @GetMapping
    public List<Genre> all() {
        return repository.findAll();
    }

}
