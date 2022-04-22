package com.costa.luiz.zero2hero.repository.springdata;

import com.costa.luiz.zero2hero.model.genre.Genre;
import com.costa.luiz.zero2hero.repository.GenreRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
@Profile("spring-data")
public interface GenreSpringRepository extends GenreRepository, JpaRepository<Genre, Long> {

    Genre findByName(String name);
}
