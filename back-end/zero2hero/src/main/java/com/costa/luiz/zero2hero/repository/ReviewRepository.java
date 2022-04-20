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
    List<Review> findAllByMovieIsNull();
    Review save(Review review);
    void deleteAllByMovie_Id(Long id);
    void deleteAllMovieIdIsNull();
}
