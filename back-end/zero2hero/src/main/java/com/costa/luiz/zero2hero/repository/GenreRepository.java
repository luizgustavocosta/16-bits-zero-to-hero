package com.costa.luiz.zero2hero.repository;

import com.costa.luiz.zero2hero.model.genre.Genre;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository {

    List<Genre> findAll();
    Optional<Genre> findById(Long id);
    Genre findByName(String name);
    Genre save(Genre genre);
}
