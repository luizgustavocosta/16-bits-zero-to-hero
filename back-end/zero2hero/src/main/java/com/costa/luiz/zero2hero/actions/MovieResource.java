package com.costa.luiz.zero2hero.actions;

import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.MovieService;
import com.costa.luiz.zero2hero.model.movie.dto.GenreMapper;
import com.costa.luiz.zero2hero.model.movie.dto.MovieDto;
import com.costa.luiz.zero2hero.model.movie.dto.MovieMapper;
import com.costa.luiz.zero2hero.model.movie.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/{username}/favorites")
    public List<Movie> getAll(@PathVariable String username) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<MovieDto> getAll() {
        List<Movie> movies = service.findAll();
        return movies.stream()
                .map(movie -> {
                    MovieDto movieDto = movieMapper.toDto(movie);
                    movieDto.setReviews(movie.getReviews()
                            .stream()
                            .map(review -> ReviewDto.builder()
                                    .id(review.getId())
                                    .build()).collect(Collectors.toUnmodifiableList()));
//                    movieDto.setGenre(movie.getGenre().stream()
//                            .map(genreMapper::toGenreKeyAndValue)
//                            .collect(Collectors.toUnmodifiableList()));
                    double random = ThreadLocalRandom.current().nextDouble(1, 5);
                    movieDto.setRating(random); //Computation
                    return movieDto;
                })
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
        Movie movie = Movie.builder()
                .id(movieDto.getId())
                .name(movieDto.getName())
                .year(movieDto.getYear())
                .country(movieDto.getCountry())
                .language(movieDto.getLanguage())
                .duration(movieDto.getDuration())
                .rating(movieDto.getRating())
                .classification(movieDto.getClassification())
                .genre(movieDto.getGenreList().stream()
                        .map(genreMapper::toGenre)
                        .collect(Collectors.toUnmodifiableList()))
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