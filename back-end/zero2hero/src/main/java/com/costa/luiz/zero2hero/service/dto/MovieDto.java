package com.costa.luiz.zero2hero.service.dto;

import com.costa.luiz.zero2hero.model.movie.Classification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private Long id;
    @NotNull
    @Size(min=2, max=50)
    private String name;
    @NotNull
    @Min(1888)
    @Max(2022)
    private int year;
    @NotNull
    @Min(value = 5, message = "Review the movie duration")
    @Max(value = 240)
    private int duration;
    @NotEmpty(message = "Please provide a country")
    private String country;
    @NotEmpty(message = "Please provide a language")
    private String language;
    private Double rating;
    private LocalDateTime lastUpdate;
    private List<Long> genreIds;
    private String genreAsString;
    private Classification classification;
    private List<ReviewDto> reviews;

}
