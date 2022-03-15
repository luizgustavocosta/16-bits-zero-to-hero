package com.costa.luiz.zero2hero.data;

import com.costa.luiz.zero2hero.model.genre.Genre;
import com.costa.luiz.zero2hero.model.genre.GenreRepository;
import com.costa.luiz.zero2hero.model.movie.Author;
import com.costa.luiz.zero2hero.model.movie.AuthorRepository;
import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.MovieRepository;
import com.costa.luiz.zero2hero.model.movie.Rating;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
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
                            .classification(Rating.get(columns[1]))
                            .language(columns[3])
                            .year(Integer.parseInt(columns[4].trim()))
                            .build();
                    movieRepository.saveAndFlush(movie);
                    movie.setGenre(genreList);
                    movieRepository.save(movie);
                });
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    @SneakyThrows
    public void insertReviews() {

    }

    private List<Genre> getGenres(String[] genres) {
        return Stream.of(genres)
                .filter(row -> !row.isEmpty())
                .map(genreRepository::findByName)
                .collect(Collectors.toUnmodifiableList());
    }
}
