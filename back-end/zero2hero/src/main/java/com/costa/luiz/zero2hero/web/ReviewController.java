package com.costa.luiz.zero2hero.web;

import com.costa.luiz.zero2hero.model.movie.Review;
import com.costa.luiz.zero2hero.repository.ReviewRepository;
import com.costa.luiz.zero2hero.service.dto.AuthorDto;
import com.costa.luiz.zero2hero.service.dto.MovieMapper;
import com.costa.luiz.zero2hero.service.dto.ReviewDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/reviews")
@RestController
@RequiredArgsConstructor
@Tag(name="Reviews")
public class ReviewController {

    private final ReviewRepository reviewRepository;

    private final MovieMapper movieMapper;

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBy(@PathVariable Long id) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            reviewRepository.save(review);
        }
    }

    @GetMapping
    public List<ReviewDto> findAll() {
        return reviewRepository.findAll().stream()
                .map(this::getReviewDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping(path = "/{id}")
    public ReviewDto findById(@PathVariable Long id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        return getReviewDto(review);
    }

    private ReviewDto getReviewDto(Review review) {
        return ReviewDto.builder()
                .movie(movieMapper.toDtoForReview(review.getMovie()))
                .id(review.getId())
                .review(review.getReview())
                .author(AuthorDto.builder()
                        .id(review.getAuthor().getId())
                        .name(review.getAuthor().getName())
                        .build())
                .build();
    }
}
