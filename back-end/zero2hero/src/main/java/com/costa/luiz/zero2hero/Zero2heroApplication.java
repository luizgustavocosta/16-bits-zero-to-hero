package com.costa.luiz.zero2hero;

import com.costa.luiz.zero2hero.model.movie.Genre;
import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.MovieRepository;
import com.costa.luiz.zero2hero.model.movie.Rating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@Slf4j
public class Zero2heroApplication {

    @Autowired
    private MovieRepository movieRepository;

    public static void main(String[] args) {
        SpringApplication.run(Zero2heroApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void insertMovies() throws IOException {
        Files.readAllLines(ResourceUtils.getFile("classpath:data/movies.csv").toPath())
                .stream()
                .skip(1)
                .forEach(row -> {
                    String[] columns = row.split(",");
                    movieRepository.save(Movie.builder()
                            .name(columns[0])
                            .classification(Rating.get(columns[1]))
                            .genre(Arrays.asList(Genre.builder().name(columns[2]).build(),
                                    Genre.builder().name("Drama").build()))
							.language(columns[3])
                            .build());
                });
    }
}
