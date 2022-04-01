package com.costa.luiz.zero2hero.data;

import com.costa.luiz.zero2hero.model.genre.Genre;
import com.costa.luiz.zero2hero.model.genre.GenreRepository;
import com.costa.luiz.zero2hero.model.movie.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profile({"in-memory", "default"})
@Slf4j
@Component
@RequiredArgsConstructor
public class LoadOnStartup {

    @Autowired
    private final MovieRepository movieRepository;

    @Autowired
    private final GenreRepository genreRepository;

    @Autowired
    private final AuthorRepository authorRepository;

    @Autowired
    private final ReviewRepository reviewRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Order(0)
    @SneakyThrows
    public void insertGenre() {
        log.info("Preparing to insert genres");
        Files.readAllLines(ResourceUtils.getFile("classpath:data/genres.csv").toPath())
                .stream()
                .skip(1)
                .forEachOrdered(row -> genreRepository.saveAndFlush(Genre.builder().name(row.trim()).build()));
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(1)
    @SneakyThrows
    public void insertAuthors() {
        log.info("Preparing to insert authors");
        Files.readAllLines(ResourceUtils.getFile("classpath:data/authors.csv").toPath())
                .stream()
                .skip(1)
                .forEachOrdered(row -> authorRepository.saveAndFlush(Author.builder().name(row.trim()).build()));
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    @SneakyThrows
    public void insertMovies() {
        log.info("Preparing to insert data");
        Files.readAllLines(ResourceUtils.getFile("classpath:data/movies.csv").toPath())
                .stream()
                .skip(1)
                .forEach(row -> {
                    String[] columns = row.split(",");
                    String[] genres = columns[2].split(" ");
                    List<Genre> genreList = getGenres(genres);
                    Movie movie = Movie.builder()
                            .name(columns[0])
                            .classification(Classification.get(columns[1]))
                            .language(columns[3])
                            .year(Integer.parseInt(columns[4].trim()))
                            .build();
                    movieRepository.save(movie);
                    movie.setGenre(genreList);
                    movieRepository.save(movie);
                });
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
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
                    reviewRepository.save(review);
                    movie.getReviews().add(review);
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

    private List<Genre> getGenres(String[] genres) {
        return Stream.of(genres)
                .filter(row -> !row.isEmpty())
                .map(genreRepository::findByName)
                .collect(Collectors.toUnmodifiableList());
    }
}
