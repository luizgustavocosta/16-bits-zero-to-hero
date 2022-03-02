package com.costa.luiz.zero2hero.actions;

import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/movies")
public class MovieResource {

    private final MovieService service;

    @GetMapping("/{username}/favorites")
    public List<Movie> getAll(@PathVariable String username) {
        return null;
    }

    @GetMapping
    public List<Movie> getAll() {
        return service.findAll();
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody Movie movie) {
        service.newMovie(movie);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Movie movie) {
        service.update(movie);
    }

    @GetMapping(path = "/{id}")
    public Movie getById(@PathVariable("id") Long id) {
        return service.findById(id);
    }
}