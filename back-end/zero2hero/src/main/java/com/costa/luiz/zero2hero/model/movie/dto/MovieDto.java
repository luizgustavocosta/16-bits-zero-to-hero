package com.costa.luiz.zero2hero.model.movie.dto;

import com.costa.luiz.zero2hero.model.movie.Classification;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MovieDto {

    private Long id;
    private String name;
    private int year;
    private int duration;
    private String country;
    private String language;
    private Double rating;
    private LocalDateTime createdAt;
    private List<GenreDto> genres;
    private List<GenreKeyValue> genre;
    private Classification classification;
    private List<ReviewDto> reviews;

}
