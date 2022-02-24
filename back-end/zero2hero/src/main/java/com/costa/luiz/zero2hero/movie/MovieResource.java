package com.costa.luiz.zero2hero.movie;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@AllArgsConstructor
public class MovieResource {

    private final MovieService service;

    @GetMapping("/movies/{username}/favorites")
    public List<Movie> getAll(@PathVariable String username) {
        return null;
    }

    @GetMapping("/movies")
    public List<Movie> getAll() {
        return service.findAll();
    }
}