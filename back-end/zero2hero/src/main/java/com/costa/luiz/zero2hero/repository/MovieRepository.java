package com.costa.luiz.zero2hero.repository;

import com.costa.luiz.zero2hero.model.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(name = "DELETE FROM movies_genre WHERE id = ?1")
    void deleteMovieById(Long id);

}
