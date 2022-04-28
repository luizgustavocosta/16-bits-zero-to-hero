package com.costa.luiz.zero2hero.persistence.repository;

import com.costa.luiz.zero2hero.persistence.repository.movie.Movie;
import com.costa.luiz.zero2hero.persistence.repository.movie.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByMovie(Movie movie);

    @Transactional
    @Modifying
    @Query(value = "delete from Review review where review.movie.id = :id")
    void deleteAllByMovieId(Long id);
}
