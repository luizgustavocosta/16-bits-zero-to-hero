package com.costa.luiz.zero2hero.actions;

import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.MovieService;
import com.costa.luiz.zero2hero.model.movie.dto.MovieDto;
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
        throw new UnsupportedOperationException();
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
    public void save(@RequestBody MovieDto movieDto) {
        Movie movie = Movie.builder()
                .name(movieDto.getName())
                .year(Integer.parseInt(movieDto.getYear()))
                .originalTitle(movieDto.getOriginalTitle())
                .build();
        //FIXME add MapStruct
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