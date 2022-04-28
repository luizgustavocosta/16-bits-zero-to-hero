package com.costa.luiz.zero2hero.persistence.repository;

import com.costa.luiz.zero2hero.persistence.repository.movie.Genre;
import com.costa.luiz.zero2hero.persistence.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    GenreRepository genreRepository;

    @Test
    void findByName() {
        var genreName = "Drama";
        var genre = Genre.builder()
                .name(genreName)
                .build();
        genreRepository.save(genre);
        var result = genreRepository.findByName(genreName);
        assertEquals(genreName, result.getName());
    }
}