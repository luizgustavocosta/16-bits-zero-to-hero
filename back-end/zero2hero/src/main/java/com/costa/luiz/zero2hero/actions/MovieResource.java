package com.costa.luiz.zero2hero.actions;

import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.MovieService;
import com.costa.luiz.zero2hero.model.movie.dto.MovieDto;
import com.costa.luiz.zero2hero.model.movie.dto.MovieMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

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
    @ResponseStatus(code = OK)
    public ResponseEntity<List<MovieDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findAll()
                        .stream()
                        .map(movieMapper::toDto)
                        .collect(Collectors.toUnmodifiableList()));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = OK)
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = OK)
    public void update(@Valid @RequestBody MovieDto movieDto) {
        service.save(movieMapper.toMovie(movieDto), movieDto.getGenreIds());
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(code = OK)
    public MovieDto getById(@PathVariable("id") Long id) {
        return movieMapper.toDto(service.findById(id));
    }

}