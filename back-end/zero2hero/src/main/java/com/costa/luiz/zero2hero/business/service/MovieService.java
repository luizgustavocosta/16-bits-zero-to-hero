package com.costa.luiz.zero2hero.business.service;

import com.costa.luiz.zero2hero.business.service.dto.MovieDto;
import com.costa.luiz.zero2hero.business.service.dto.MovieMapper;
import com.costa.luiz.zero2hero.persistence.repository.GenreRepository;
import com.costa.luiz.zero2hero.persistence.repository.MovieRepository;
import com.costa.luiz.zero2hero.persistence.repository.ReviewRepository;
import com.costa.luiz.zero2hero.persistence.repository.movie.Genre;
import com.costa.luiz.zero2hero.persistence.repository.movie.Movie;
import com.costa.luiz.zero2hero.persistence.repository.movie.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final GenreRepository genreRepository;
    private final MovieMapper movieMapper;

    public List<MovieDto> findAll() {
        return movieRepository.findAll()
                .stream()
                .peek(movie -> {
                    List<Review> allByMovie = reviewRepository.findAllByMovie(movie);
                    movie.setReviews(allByMovie);
                })
                .map(movieMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public MovieDto findById(Long id) {
        return movieRepository.findById(id).map(movieMapper::toDto).orElseThrow();
    }

    @Transactional
    public void deleteById(Long id) {
        reviewRepository.deleteAllByMovieId(id);
        movieRepository.deleteById(id);
    }

    public void save(MovieDto movieDto) {
        Movie movie = movieMapper.toMovie(movieDto);
        Set<Genre> genres = movieDto.getGenreIds().stream()
                .filter(Objects::nonNull)
                .map(id -> genreRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableSet());
        movie.setGenre(genres);
        movieRepository.save(movie);
    }
}
