package com.costa.luiz.zero2hero.model.movie.dto;

import com.costa.luiz.zero2hero.model.movie.Review;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AuthorDto {

    private Long id;
    private String name;
    private List<Review> reviews;
}
