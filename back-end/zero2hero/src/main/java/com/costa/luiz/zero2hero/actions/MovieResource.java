package com.costa.luiz.zero2hero.actions;

import com.costa.luiz.zero2hero.model.genre.Genre;
import com.costa.luiz.zero2hero.model.genre.GenreRepository;
import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.MovieService;
import com.costa.luiz.zero2hero.model.movie.dto.GenreMapper;
import com.costa.luiz.zero2hero.model.movie.dto.MovieDto;
import com.costa.luiz.zero2hero.model.movie.dto.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieResource {

    private final MovieService service;

    @Autowired
    private final GenreMapper genreMapper;

    @Autowired
    private final MovieMapper movieMapper;

    @Autowired
    GenreRepository genreRepository;

    @GetMapping("/{username}/favorites")
    public List<Movie> getAll(@PathVariable String username) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<MovieDto> getAll() {
        List<Movie> movies = service.findAll();
        List<MovieDto> collect = movies.stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
        return collect;
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
        //FIXME - Move to service
        List<Genre> genres = movieDto.getGenreIds().stream()
                .filter(Objects::nonNull)
                .map(id -> genreRepository.findById(id).get())
                .collect(Collectors.toUnmodifiableList());
        Movie movie = Movie.builder()
                .id(movieDto.getId())
                .name(movieDto.getName())
                .year(movieDto.getYear())
                .country(movieDto.getCountry())
                .language(movieDto.getLanguage())
                .duration(movieDto.getDuration())
                .rating(movieDto.getRating())
                .classification(movieDto.getClassification())
                .genre(genres)
                .build();
        service.update(movie);
    }

    @GetMapping(path = "/{id}")
    public MovieDto getById(@PathVariable("id") Long id) {
        Movie movie = service.findById(id);
        MovieDto movieDto = movieMapper.toDto(movie);
        return movieDto;
    }
}