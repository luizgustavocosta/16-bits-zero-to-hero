package com.costa.luiz.zero2hero.business.service;

import com.costa.luiz.zero2hero.business.service.dto.AuthorDto;
import com.costa.luiz.zero2hero.business.service.dto.MovieMapper;
import com.costa.luiz.zero2hero.business.service.dto.ReviewDto;
import com.costa.luiz.zero2hero.persistence.repository.ReviewRepository;
import com.costa.luiz.zero2hero.persistence.repository.movie.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieMapper movieMapper;

    public ReviewDto findById(Long id) {
        return reviewRepository.findById(id).map(this::getReviewDto).orElseThrow();
    }

    public void save(Review review) {
        throw new UnsupportedOperationException();
    }

    public List<ReviewDto> findAll() {
        return reviewRepository.findAll().stream()
                .map(this::getReviewDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
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
