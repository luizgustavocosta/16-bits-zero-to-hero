package com.costa.luiz.zero2hero.repository;

import com.costa.luiz.zero2hero.model.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findByName(String name);
}
