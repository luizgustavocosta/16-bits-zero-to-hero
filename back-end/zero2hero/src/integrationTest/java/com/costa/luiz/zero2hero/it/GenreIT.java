package com.costa.luiz.zero2hero.it;

import com.costa.luiz.zero2hero.persistence.repository.GenreRepository;
import com.costa.luiz.zero2hero.persistence.repository.movie.Genre;
import com.costa.luiz.zero2hero.presentation.web.movie.GenreController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("it")
class GenreIT {

    @Autowired
    GenreController genreController;

    @Autowired
    GenreRepository genreRepository;

    @Test
    void findAllGenres() {
        var newGenre = "IT Programming";
        genreRepository.save(Genre.builder().name(newGenre).build());
        var genres = genreController.all();
        assertEquals(1, genres.size());
        assertEquals(newGenre, genres.iterator().next().getName());
    }
}
