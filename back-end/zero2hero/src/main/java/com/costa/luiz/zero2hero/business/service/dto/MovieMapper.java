package com.costa.luiz.zero2hero.business.service.dto;

import com.costa.luiz.zero2hero.persistence.repository.movie.Movie;
import com.costa.luiz.zero2hero.persistence.repository.movie.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Mapping(target = "genreAsString", expression = "java(String.join(\",\"," +
            "movie.getGenre().stream()" +
            ".map(com.costa.luiz.zero2hero.persistence.repository.movie.Genre::getName)" +
            ".collect(java.util.stream.Collectors.toList())))")
    @Mapping(target = "reviews", expression = "java(reviews(movie.getReviews()))")
    @Mapping(target = "genreIds", expression = "java(movie.getGenre().stream().map(com.costa.luiz.zero2hero.persistence.repository.movie.Genre::getId).collect(java.util.stream.Collectors.toList()))")
    MovieDto toDto(Movie movie);

    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "genreAsString", ignore = true)
    @Mapping(target = "genreIds", expression = "java(movie.getGenre().stream().map(com.costa.luiz.zero2hero.persistence.repository.movie.Genre::getId).collect(java.util.stream.Collectors.toList()))")
    MovieDto toDtoForReview(Movie movie);

    @Mapping(target = "genre", ignore = true)
    Movie toMovie(MovieDto movieDto);

    default List<ReviewDto> reviews(List<Review> reviews) {
        return
                reviews.stream()
                        .map(review -> ReviewDto.builder()
                                .id(review.getId())
                                .author(AuthorDto.builder()
                                        .id(review.getAuthor().getId())
                                        .name(review.getAuthor().getName())
                                        .build())
                                .review(review.getReview())
                                .build()).collect(Collectors.toUnmodifiableList());
    }
}
