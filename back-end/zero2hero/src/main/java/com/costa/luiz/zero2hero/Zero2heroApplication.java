package com.costa.luiz.zero2hero;

import com.costa.luiz.zero2hero.model.movie.Movie;
import com.costa.luiz.zero2hero.model.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Zero2heroApplication {

	@Autowired
	private MovieRepository movieRepository;

	public static void main(String[] args) {
		SpringApplication.run(Zero2heroApplication.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void insertMovies() {
//		movieRepository.save(new Movie("The Shawshank Redemption", "Drama", "1994"));
//		movieRepository.save(new Movie("The Godfather", "Crime", "1972"));
//		movieRepository.save(new Movie("The Godfather: Part II", "Crime", "1974"));
//		movieRepository.save(new Movie("The Dark Knight", "Action", "2008"));
//	}

}
