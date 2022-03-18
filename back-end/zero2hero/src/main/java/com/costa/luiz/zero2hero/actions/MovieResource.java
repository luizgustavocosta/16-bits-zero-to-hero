package com.costa.luiz.zero2hero.actions;

import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.MovieService;
import com.costa.luiz.zero2hero.model.movie.dto.GenreMapper;
import com.costa.luiz.zero2hero.model.movie.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieResource {

    private final MovieService service;

    @Autowired
    private final GenreMapper genreMapper;

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
                .year(movieDto.getYear())
                .build();
        //FIXME add MapStruct
        service.newMovie(movie);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody MovieDto movieDto) {
        Movie movie = Movie.builder()
                .id(movieDto.getId())
                .name(movieDto.getName())
                .year(movieDto.getYear())
                .genre(movieDto.getGenres().stream()
                        .map(genreMapper::toGenre)
                        .collect(Collectors.toUnmodifiableList()))
                .build();
        service.update(movie);
    }

    @GetMapping(path = "/{id}")
    public Movie getById(@PathVariable("id") Long id) {
        return service.findById(id);
    }
}