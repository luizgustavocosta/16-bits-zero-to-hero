package com.costa.luiz.zero2hero.repository.springdata;

import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.repository.MovieRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Profile("spring-data")
public interface MovieSpringRepository extends MovieRepository, JpaRepository<Movie, Long> {

    @Query(name = "DELETE FROM movies_genre WHERE id = ?1")
    void deleteMovieById(Long id);

}
