package com.costa.luiz.zero2hero.model.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long id;
    private AuthorDto author;
    private String review;
    private boolean archived;
    private MovieDto movie;
}
