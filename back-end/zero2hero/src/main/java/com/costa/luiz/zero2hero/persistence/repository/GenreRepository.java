package com.costa.luiz.zero2hero.persistence.repository;

import com.costa.luiz.zero2hero.persistence.repository.movie.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findByName(String name);
}
