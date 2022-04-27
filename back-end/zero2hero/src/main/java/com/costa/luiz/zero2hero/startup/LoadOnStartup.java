package com.costa.luiz.zero2hero.startup;

import com.costa.luiz.zero2hero.model.movie.Genre;
import com.costa.luiz.zero2hero.model.movie.Author;
import com.costa.luiz.zero2hero.model.movie.Classification;
import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.Review;
import com.costa.luiz.zero2hero.repository.AuthorRepository;
import com.costa.luiz.zero2hero.repository.GenreRepository;
import com.costa.luiz.zero2hero.repository.MovieRepository;
import com.costa.luiz.zero2hero.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profile({"in-memory", "default"})
@Slf4j
@Component
@RequiredArgsConstructor
public class LoadOnStartup {

    private final MovieRepository movieRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    private final ReviewRepository reviewRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Order(0)
    @SneakyThrows
    public void insertGenre() {
        log.info("Preparing to insert genres");
        Files.readAllLines(ResourceUtils.getFile("classpath:data/genres.csv").toPath())
                .stream()
                .skip(1)
                .forEachOrdered(row -> genreRepository.save(Genre.builder().name(row.trim()).build()));
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(1)
    @SneakyThrows
    public void insertAuthors() {
        log.info("Preparing to insert authors");
        Files.readAllLines(ResourceUtils.getFile("classpath:data/authors.csv").toPath())
                .stream()
                .skip(1)
                .forEachOrdered(row -> authorRepository.save(Author.builder().name(row.trim()).build()));
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    @SneakyThrows
    @Transactional
    public void insertMovies() {
        log.info("Preparing to insert data");
        Files.readAllLines(ResourceUtils.getFile("classpath:data/movies.csv").toPath())
                .stream()
                .skip(1)
                .forEach(row -> {
                    String[] columns = row.split(",");
                    String[] genres = columns[2].split(" ");
                    Set<Genre> genreList = getGenres(genres);
                    new ArrayList<>(genreList).forEach(genreRepository::save);
                    Movie movie = Movie.builder()
                            .name(columns[0].trim())
                            .classification(Classification.get(columns[1].trim()))
                            .language(columns[3].trim())
                            .year(Integer.parseInt(columns[4].trim()))
                            .country(columns[5].trim())
                            .duration(Integer.parseInt(columns[6].trim()))
                            .rating(Double.parseDouble(columns[7].trim()))
                            .reviews(Collections.emptyList())
                            .genre(genreList)
                            .build();
                    movieRepository.save(movie);
                });
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(3)
    @SneakyThrows
    @Transactional
    public void insertReviews() {
        //FIXME - It's confusing
        AtomicInteger atomicInteger = new AtomicInteger();
        movieRepository.findAll()
                .forEach(movie -> {
                    Author author = authorRepository.findAll().get(atomicInteger.incrementAndGet());
                    if (ThreadLocalRandom.current().nextInt() % 2 == 0) {
                        Review review = createReview(movie, author);
                        author.getReviews().add(review);
                        movie.getReviews().add(review);
                    }
                    Review review = createReview(movie, author);
                    author.getReviews().add(review);
                    movie.getReviews().add(review);
                    reviewRepository.save(review);
                    authorRepository.save(author);
                    movieRepository.save(movie);
                });
        log.info("Reviews inserted");
    }

    private Review createReview(Movie movie, Author author) {
        return Review.builder()
                .review(Instant.now().toString())
                .author(author)
                .movie(movie)
                .build();
    }

    private Set<Genre> getGenres(String[] genres) {
        return Stream.of(genres)
                .filter(row -> !row.isEmpty())
                .map(genreRepository::findByName)
                .collect(Collectors.toUnmodifiableSet());
    }
}
