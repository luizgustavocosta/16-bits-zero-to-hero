package com.costa.luiz.zero2hero.model.movie.dto;

import com.costa.luiz.zero2hero.model.movie.Rating;
import com.costa.luiz.zero2hero.model.movie.Review;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MovieDto {

    private Long id;
    private String name;
    private int year;
    private String title;
    private String originalTitle;
    private int duration;
    private String country;
    private String language;
    private LocalDateTime createdAt;
    private List<GenreDto> genres;
    private Rating classification;
    private List<Review> comments;

}
