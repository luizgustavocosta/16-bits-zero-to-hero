package com.costa.luiz.zero2hero.actions;

import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.MovieService;
import com.costa.luiz.zero2hero.model.movie.dto.MovieDto;
import com.costa.luiz.zero2hero.model.movie.dto.MovieMapper;
import com.costa.luiz.zero2hero.repository.jdbc.MovieJDBCRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@Tag(name="Movies")
@RequestMapping("/movies")
public class MovieResource {

    private final MovieService service;

    private final MovieMapper movieMapper;

    @Operation(summary = "Find movies by user")
    @GetMapping("/{username}/favorites")
    public List<Movie> getAll(@PathVariable String username) {
        throw new UnsupportedOperationException();
    }

    @GetMapping
    @Operation(summary = "Return all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    public ResponseEntity<List<MovieDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAll()
                        .stream()
                        .map(movieMapper::toDto)
                        .collect(Collectors.toUnmodifiableList()));
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


    MovieJDBCRepository movieJDBCRepository;

    @GetMapping("jdbc")
    public List<Movie> findAllByJDBC() {
        List<Movie> movies = movieJDBCRepository.findAll();
        return movies;
    }
}