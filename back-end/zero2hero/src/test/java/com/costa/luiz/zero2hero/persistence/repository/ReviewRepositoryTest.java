package com.costa.luiz.zero2hero.persistence.repository;

import com.costa.luiz.zero2hero.persistence.repository.movie.Movie;
import com.costa.luiz.zero2hero.persistence.repository.movie.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Review Repository Test")
@DataJpaTest
@ExtendWith(SpringExtension.class)
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    private final Movie movie = Movie.builder().id(4L).build();

    @DisplayName("Find all reviews based on movie")
    @Test
    @Sql("/insert_movie_reviews.sql")
    void findAllByMovie() {
        List<Review> reviews = reviewRepository.findAllByMovie(movie);
        assertEquals(2, reviews.size());
    }

    @DisplayName("Delete all reviews based on movie")
    @Test
    @Sql("/insert_movie_reviews.sql")
    void deleteAllByMovieId() {
        assertEquals(2, reviewRepository.findAllByMovie(movie).size());

        reviewRepository.deleteAllByMovieId(movie.getId());
        assertTrue(reviewRepository.findAllByMovie(movie).isEmpty());
    }
}