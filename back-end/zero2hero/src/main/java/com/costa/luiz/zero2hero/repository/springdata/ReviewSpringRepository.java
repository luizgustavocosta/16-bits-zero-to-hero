package com.costa.luiz.zero2hero.repository.springdata;

import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.Review;
import com.costa.luiz.zero2hero.repository.ReviewRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Profile("spring-data")
public interface ReviewSpringRepository extends ReviewRepository, JpaRepository<Review, Long> {

    List<Review> findAllByMovie(Movie movie);

    @Transactional
    @Modifying
    @Query(value = "delete from Review review where review.movie.id = :id")
    void deleteAllByMovieId(Long id);


}
