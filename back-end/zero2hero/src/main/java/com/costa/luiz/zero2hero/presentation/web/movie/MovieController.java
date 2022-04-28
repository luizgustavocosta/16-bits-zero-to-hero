package com.costa.luiz.zero2hero.presentation.web.movie;

import com.costa.luiz.zero2hero.business.service.MovieService;
import com.costa.luiz.zero2hero.business.service.dto.MovieDto;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@Tag(name = "Movies")
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;

    @Operation(summary = "Find movies by user")
    @GetMapping("/{username}/favorites")
    public List<MovieDto> getAll(@PathVariable String username) {
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
                .body(service.findAll());
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = OK)
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = OK)
    public void update(@Valid @RequestBody MovieDto movieDto) {
        service.save(movieDto);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(code = OK)
    public MovieDto getById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

}