package com.costa.luiz.zero2hero.movie;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void insertMovies() {
        movieRepository.save(new Movie("The Shawshank Redemption", "Drama", "1994"));
        movieRepository.save(new Movie("The Godfather", "Crime", "1972"));
        movieRepository.save(new Movie("The Godfather: Part II", "Crime", "1974"));
        movieRepository.save(new Movie("The Dark Knight", "Action", "2008"));
    }
}
