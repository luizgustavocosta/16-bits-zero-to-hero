package com.costa.luiz.zero2hero.model.movie.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovieDto {

    private String id;
    private String genre;
    private String name;
    private String year;
    private String title;
    private String originalTitle;
    private Long duration;
    private String country;
    private String language;
    private LocalDateTime creationDateAndTime;

}
