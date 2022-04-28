package com.costa.luiz.zero2hero.business.service;

import com.costa.luiz.zero2hero.business.service.dto.MovieDto;
import com.costa.luiz.zero2hero.business.service.dto.MovieMapper;
import com.costa.luiz.zero2hero.persistence.repository.GenreRepository;
import com.costa.luiz.zero2hero.persistence.repository.MovieRepository;
import com.costa.luiz.zero2hero.persistence.repository.ReviewRepository;
import com.costa.luiz.zero2hero.persistence.repository.movie.Movie;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private  MovieMapper movieMapper;

    private MovieService movieService;
    private Movie movie;
    private MovieDto movieDto;
    private final long ID = 42L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieService = new MovieService(movieRepository, reviewRepository, genreRepository, movieMapper);
        movie = Movie.builder().id(ID).name("Matrix").build();
        movieDto = MovieDto.builder().id(ID).name("Matrix I")
                .genreIds(Collections.emptyList())
                .build();
    }

    @Test
    void findAll() {
        doReturn(Collections.singletonList(movie)).when(movieRepository).findAll();
        doReturn(null).when(reviewRepository).findAllByMovie(any(Movie.class));
        doReturn(movieDto).when(movieMapper).toDto(any(Movie.class));

        List<MovieDto> movies = movieService.findAll();

        assertThat(movies).as("List of movies").asList()
                .extracting("id")
                .contains(ID)
                .doesNotContain(2);

        verify(movieRepository).findAll();
        verify(reviewRepository).findAllByMovie(any(Movie.class));
        verify(movieMapper).toDto(any(Movie.class));
    }

    @Test
    void findById() {
        doReturn(Optional.of(movie)).when(movieRepository).findById(ID);
        doReturn(movieDto).when(movieMapper).toDto(movie);

        MovieDto movieDtoById = movieService.findById(ID);

        assertThat(movieDtoById).as("Movie should have an id and name")
                .extracting("id","name")
                .isNotNull();
    }

    @Test
    void deleteById() {
        doNothing().when(reviewRepository).deleteAllByMovieId(ID);
        doNothing().when(movieRepository).deleteById(ID);

        movieService.deleteById(ID);

        verify(reviewRepository).deleteAllByMovieId(ID);
        verify(movieRepository).deleteById(ID);
    }

    @Test
    void save() {
        doReturn(movie).when(movieMapper).toMovie(movieDto);
        doReturn(null).when(genreRepository).findById(any(Long.class));
        movieService.save(movieDto);
        verify(movieRepository).save(any(Movie.class));
    }
}