package com.costa.luiz.zero2hero.model.movie;

import com.costa.luiz.zero2hero.model.genre.Genre;
import com.costa.luiz.zero2hero.repository.GenreRepository;
import com.costa.luiz.zero2hero.repository.MovieRepository;
import com.costa.luiz.zero2hero.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    private final GenreRepository genreRepository;

    public List<Movie> findAll() {
        return movieRepository.findAll()
                .stream()
                .peek(movie -> {
                    List<Review> allByMovie = reviewRepository.findAllByMovie(movie);
                    movie.setReviews(allByMovie);
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void deleteById(Long id) {
        reviewRepository.deleteAllByMovieId(id);
        movieRepository.deleteById(id);
    }

    public void update(Movie movie, List<Long> genreIds) {
        Set<Genre> genres = genreIds.stream()
                .filter(Objects::nonNull)
                .map(id -> genreRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableSet());
        movie.setGenre(genres);
        movieRepository.save(movie);
    }

    public void newMovie(Movie movie) {
        if (nonNull(movie.getId())) {
            throw new IllegalArgumentException("Id must be null");
        }
        movieRepository.save(movie);
    }
}
