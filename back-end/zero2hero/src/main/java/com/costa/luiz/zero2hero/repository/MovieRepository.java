package com.costa.luiz.zero2hero.repository;

import com.costa.luiz.zero2hero.model.movie.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {

    List<Movie> findAll();
    Optional<Movie> findById(Long id);
    Movie save(Movie movie);
    void deleteById(Long id);
}
