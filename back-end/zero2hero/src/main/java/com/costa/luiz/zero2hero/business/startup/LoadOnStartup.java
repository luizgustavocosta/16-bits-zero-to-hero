package com.costa.luiz.zero2hero.business.startup;

import com.costa.luiz.zero2hero.persistence.repository.AuthorRepository;
import com.costa.luiz.zero2hero.persistence.repository.GenreRepository;
import com.costa.luiz.zero2hero.persistence.repository.MovieRepository;
import com.costa.luiz.zero2hero.persistence.repository.ReviewRepository;
import com.costa.luiz.zero2hero.persistence.repository.movie.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@Profile({"in-memory"})
@Slf4j
@Component
@RequiredArgsConstructor
public class LoadOnStartup {

    private final MovieRepository movieRepository;

    private final GenreRepository genreRepository;

    private final AuthorRepository authorRepository;

    private final ReviewRepository reviewRepository;

    private final ClassLoader classLoader = this.getClass().getClassLoader();

    @EventListener(ApplicationReadyEvent.class)
    @Order(0)
    public void insertGenre() {
        log.info("Preparing to insert genres");
        readCSV("genres.csv")
                .forEach(row -> genreRepository.save(Genre.builder().name(row.trim()).build()));
        log.info("Rows inserted {}", genreRepository.findAll().size());
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(1)
    @SneakyThrows
    public void insertAuthors() {
        log.info("Preparing to insert authors");
        readCSV("authors.csv")
                .forEach(row -> authorRepository.save(Author.builder().name(row.trim()).build()));
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    @SneakyThrows
    @Transactional
    public void insertMovies() {
        log.info("Preparing to insert data");
        readCSV("movies.csv")
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

    public List<String> readCSV(String fileName) {
        try (InputStream resourceAsStream = classLoader.getResourceAsStream("data/" + fileName)) {
            if (nonNull(resourceAsStream)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
                return reader.lines()
                        .skip(1)
                        .collect(Collectors.toUnmodifiableList());
            }
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
        }
        return Collections.emptyList();
    }
}
