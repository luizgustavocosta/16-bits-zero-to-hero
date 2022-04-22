package com.costa.luiz.zero2hero.repository;

import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.Review;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository {

    Optional<Review> findById(Long id);
    List<Review> findAll();
    List<Review> findAllByMovie(Movie movie);
    Review save(Review review);
    void deleteAllByMovieId(Long id);
}
