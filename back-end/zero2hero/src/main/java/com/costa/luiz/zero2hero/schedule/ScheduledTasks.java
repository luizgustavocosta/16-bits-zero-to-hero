package com.costa.luiz.zero2hero.schedule;

import com.costa.luiz.zero2hero.model.movie.AuthorRepository;
import com.costa.luiz.zero2hero.model.movie.Review;
import com.costa.luiz.zero2hero.model.movie.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ScheduledTasks {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Scheduled(fixedRate = 120000)
    public void removeOrphanReviewWithoutMovies() {
        List<Review> reviews = reviewRepository.findAllByMovieIsNull();
        log.info("Found {} reviews without movies", reviews.size());
        if (!reviews.isEmpty()) {
            reviewRepository.deleteAllMovieIdIsNull();
        }
    }

    @Scheduled(fixedRate = 120000)
    public void calculateMovieRating() {
        log.warn("To be done.....");
    }
}
