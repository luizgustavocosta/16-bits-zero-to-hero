package com.costa.luiz.zero2hero.actions;

import com.costa.luiz.zero2hero.model.genre.GenreRepository;
import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.MovieService;
import com.costa.luiz.zero2hero.model.movie.dto.MovieDto;
import com.costa.luiz.zero2hero.model.movie.dto.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieResource {

    private final MovieService service;


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
        return service.findAll().stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
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
        service.update(movieMapper.toMovie(movieDto), movieDto.getGenreIds());
    }

    @GetMapping(path = "/{id}")
    public MovieDto getById(@PathVariable("id") Long id) {
        return movieMapper.toDto(service.findById(id));
    }
}