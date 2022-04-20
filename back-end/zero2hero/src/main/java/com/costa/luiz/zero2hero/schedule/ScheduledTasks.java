package com.costa.luiz.zero2hero.schedule;

import com.costa.luiz.zero2hero.model.movie.Review;
import com.costa.luiz.zero2hero.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

    private final ReviewRepository reviewRepository;

    @Scheduled(fixedRate = 120000)
    public void removeOrphanReviewWithoutMovies() {
//        List<Review> reviews = reviewRepository.findAllByMovieIsNull();
//        log.info("Found {} reviews without movies", reviews.size());
//        if (!reviews.isEmpty()) {
//            reviewRepository.deleteAllMovieIdIsNull();
//        }
    }

}
