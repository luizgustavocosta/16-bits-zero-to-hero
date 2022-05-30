package com.costa.luiz.zero2hero.it;

import com.costa.luiz.zero2hero.business.service.dto.MovieDto;
import com.costa.luiz.zero2hero.persistence.repository.GenreRepository;
import com.costa.luiz.zero2hero.persistence.repository.MovieRepository;
import com.costa.luiz.zero2hero.persistence.repository.movie.Genre;
import com.costa.luiz.zero2hero.presentation.web.movie.MovieController;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("it")
@DisplayName("Movie Integration tests")
class MovieIT {

    @Autowired
    MovieController movieController;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    GenreRepository genreRepository;

    String GENRE_NAME = "Movie genre";
    String FIRST_MOVIE_NAME = "Samba";
    String FIRST_MOVIE_COUNTRY = "Brazil";
    String FIRST_MOVIE_LANGUAGE = "Portuguese";
    Long movieId = null;

    @BeforeEach
    void setUp() {
        genreRepository.saveAndFlush(Genre.builder().name(GENRE_NAME).build());
        var movie = movieDTOBuilder();
        movieController.update(movie.build());
        movieId = movieRepository.findAll().stream().findFirst().get().getId();
    }

    private MovieDto.MovieDtoBuilder movieDTOBuilder() {
        return MovieDto.builder()
                .name(FIRST_MOVIE_NAME)
                .country(FIRST_MOVIE_COUNTRY)
                .duration(200)
                .genreIds(Collections.singletonList(1L))
                .language(FIRST_MOVIE_LANGUAGE)
                .reviews(Collections.emptyList())
                .rating(7d)
                .year(2022);
    }

    @DisplayName("Create")
    @Test
    @Order(1)
    void createANewMovie() {
        var name = "Carnival";
        var country = "Brazil";
        var language = "English";
        var movieDto = movieDTOBuilder()
                .name(name)
                .country(country)
                .language(language)
                .build();

        movieController.update(movieDto);

        List<MovieDto> body = movieController.getAll().getBody();
        assertAll(() -> {
            assertEquals(2, body.size());
            assertThat(body)
                    .extracting("name", "country", "language", "genreAsString")
                    .contains(tuple(name, country, language, GENRE_NAME), atIndex(1));
        });
    }

    @DisplayName("Retrieve by id")
    @Test
    @Order(2)
    void retrieveAMovie() {
        MovieDto movieDto = movieController.getById(movieId);
        assertEquals(movieId, movieDto.getId());
        assertEquals(FIRST_MOVIE_NAME, movieDto.getName());
    }

    @DisplayName("Retrieve all")
    @Test
    @Order(3)
    void retrieveAllMovies() {
        ResponseEntity<List<MovieDto>> movies = movieController.getAll();
        assertAll(() -> {
            assertTrue(movies.getStatusCode().is2xxSuccessful());
            assertEquals(1, movies.getBody().size());
        });
    }

    @DisplayName("Update")
    @Test
    @Order(4)
    void updateAMovie() {
        var modifiedCountry = "Andorra";
        var modifiedLanguage = "Catalán";
        var modifiedRating = 10d;

        MovieDto movieDto = movieDTOBuilder()
                .id(movieId)
                .country(modifiedCountry)
                .language(modifiedLanguage)
                .rating(modifiedRating)
                .build();

        movieController.update(movieDto);

        List<MovieDto> body = movieController.getAll().getBody();
        assertAll(() -> {
            assertEquals(1, body.size());
            assertThat(body)
                    .extracting("country", "language", "rating")
                    .contains(tuple(modifiedCountry, modifiedLanguage, modifiedRating));
        });
    }

    @DisplayName("Delete")
    @Order(5)
    @Test
    void deleteAMovie() {
        movieController.delete(movieId);
        ResponseEntity<List<MovieDto>> movies = movieController.getAll();
        assertAll(() -> {
            assertEquals(HttpStatus.OK, movies.getStatusCode());
            assertTrue(movies.getBody().isEmpty());
        });
    }
}
